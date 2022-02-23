package com.pentagon.cafe.virtualSmallJobFinder.payload;

import com.pentagon.cafe.virtualSmallJobFinder.validation.ValidPassword;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class RegisterRequest {

    /***
     * password requirements
     * 8-30 characters, min 1 lowercase, min 1 uppercase, min 1 special character, min 1 digit, no whitespace
     */

    @NotNull
    @NotEmpty
    @Email
    private String email;
    @NotNull
    @NotBlank(message = "Username is mandatory")
    @Length(min = 5, max = 25)
    private String username;
    @ValidPassword
    private String password;
}
