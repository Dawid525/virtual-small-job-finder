package com.pentagon.cafe.virtualSmallJobFinder.utils;

import lombok.Data;
import java.util.Set;

@Data
public class RegisterRequest {

    private String email;
    private String username;
    private String password;
    private Set<String> roles;
}
