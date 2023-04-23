package com.example.backend2;

import lombok.Data;

@Data
public class UpdatePWDRequest {

    private String oldPWD;
    private String newPWD;
}
