package com.example.backend2;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    User findByUsername(String username);
    void insert(User user);

    void update(User currentUser);

    void updatePassword(User currentUser);

    List<User> findAllUsers();
}
