package com.example.backend2;


import lombok.Data;

@Data
public class User {
    private String username;

    private String name;
    private String idCard;

    private String password;
    private String phone;
    private String email;

    public User() {
    }

    public User(String name, String username, String idCard, String password, String phone, String email) {
        this.name = name;
        this.username = username;
        this.idCard = idCard;
        this.password = password;
        this.phone = phone;
        this.email = email;
    }

}

