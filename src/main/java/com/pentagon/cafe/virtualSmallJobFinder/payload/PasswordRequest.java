package com.pentagon.cafe.virtualSmallJobFinder.payload;

import com.pentagon.cafe.virtualSmallJobFinder.validation.ValidPassword;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PasswordRequest {
    @NotNull(message = "Password is mandatory")
    @NotBlank
    @ValidPassword
    private String password;
}
