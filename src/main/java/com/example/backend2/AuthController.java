package com.example.backend2;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin-api/user")
public class AuthController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @PostMapping("/login")
    public CommonResult<?> login(@RequestBody LoginRequest loginUser) {

        User user = userMapper.findByUsername(loginUser.getUsername());

        if (user == null) {
            return CommonResult.error(50007,"登录失败，账号密码不正确");
        }

        if (!loginUser.getPassword().equals(user.getPassword())) {
            return CommonResult.error(50007,"登录失败，账号密码不正确");
        }

        String username = loginUser.getUsername();

        // 生成访问令牌和刷新令牌
        String accessToken = jwtTokenUtil.generateAccessToken(username);
        String refreshToken = jwtTokenUtil.generateRefreshToken(username);
        TokenResponse token_resp = new TokenResponse(accessToken,refreshToken);

        CommonResult<TokenResponse> result = CommonResult.success(token_resp);

        return result;
    }

    @PostMapping("/register")
    public CommonResult<?> register(@RequestBody RegisterRequest reg_user) {

        System.out.println(reg_user.toString());
        User foundUser = userMapper.findByUsername(reg_user.getUsername());

        if (foundUser != null) {
            return CommonResult.error(50003,"用户已存在");
        }

        User new_user = new User(reg_user.getName(),reg_user.getUsername()
                ,reg_user.getIdCard(),reg_user.getPassword(),reg_user.getPhone()
                ,reg_user.getEmail());
        try {
            // 将用户信息保存到数据库
            userMapper.insert(new_user);
        } catch (Exception e) {
            // 处理插入失败的情况
            System.out.println(e.toString());
            return CommonResult.error(50003,"User registration failed");
        }
        return CommonResult.success("User registered successfully");
    }
}
