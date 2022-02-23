package com.pentagon.cafe.virtualSmallJobFinder.payload;

import com.pentagon.cafe.virtualSmallJobFinder.validation.ValidPassword;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class LoginRequest {

    @NotNull(message = "Username is mandatory")
    @NotBlank
    @Length(min = 5, max = 25, message = "Username must have 5-25 characters")
    private String username;
    @NotNull(message = "Password is mandatory")
    @NotBlank
    @ValidPassword
    private String password;
}
