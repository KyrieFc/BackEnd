package com.example.backend2;

import lombok.Data;

@Data
public class RegisterRequest {

    private String name;

    private String username;

    private String idCard;

    private String password;

    private String phone;

    private String email;
}
