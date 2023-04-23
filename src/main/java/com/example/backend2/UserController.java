package com.example.backend2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/admin-api/user/")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @GetMapping("/profile/get")
    public CommonResult<?> getUserProfile(@RequestHeader("Authorization") String authHeader) {

        // 解析Authorization请求头中的JWT令牌 Bearer access_token
        String token = authHeader.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);

        User foundUser = userMapper.findByUsername(username);

        CommonResult<User> result = CommonResult.success(foundUser);
        return result;
    }

    @GetMapping("/getUsers")
    public CommonResult<List<User>> getUserList() {
        List<User> userList = userMapper.findAllUsers();

        System.out.println("测试:" + userList);

        return CommonResult.success(userList);
    }


    @PutMapping("/update-profile")
    public CommonResult<?> updateProfile(@RequestHeader("Authorization") String token, @RequestBody User user) {
        // 从 token 中获取用户信息，如下示例中假设使用 JWT Token
        String jwtToken = token.substring(7); // 去掉 "Bearer " 前缀
        String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        User currentUser = userMapper.findByUsername(username);

        // 更新用户信息
        currentUser.setName(user.getName());
        currentUser.setPassword(user.getPassword());
        currentUser.setPhone(user.getPhone());
        currentUser.setEmail(user.getEmail());

        // 保存更新后的用户信息
        userMapper.update(currentUser);

        return CommonResult.success("Profile updated successfully");
    }

    //实现修改密码
    @PostMapping("/updatePassword")
    public CommonResult<?> updatePassword(@RequestHeader("Authorization") String token,
                                          @RequestBody UpdatePWDRequest updatePWDRequest) {
        // 从 token 中获取用户信息，如下示例中假设使用 JWT Token
        String jwtToken = token.substring(7); // 去掉 "Bearer " 前缀
        String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        User currentUser = userMapper.findByUsername(username);

        System.out.println("submitOldPWD:"+updatePWDRequest.getOldPWD());
        System.out.println("oldPWD:"+currentUser.getPassword());
        if(!Objects.equals(updatePWDRequest.getOldPWD(), currentUser.getPassword())){
            return CommonResult.error(50007,"旧密码错误");
        }
        // 更新用户信息
        currentUser.setPassword(updatePWDRequest.getNewPWD());

        // 保存更新后的用户信息
        userMapper.updatePassword(currentUser);

        return CommonResult.success("Password updated successfully");
    }
}