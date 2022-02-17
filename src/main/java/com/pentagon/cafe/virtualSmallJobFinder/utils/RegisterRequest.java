package com.pentagon.cafe.virtualSmallJobFinder.utils;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String username;
    private String password;
}
