package com.pentagon.cafe.virtualSmallJobFinder.payload;

import com.pentagon.cafe.virtualSmallJobFinder.validation.ValidPassword;
import lombok.Data;

@Data
public class PasswordRequest {
    @ValidPassword
    private String password;
}
