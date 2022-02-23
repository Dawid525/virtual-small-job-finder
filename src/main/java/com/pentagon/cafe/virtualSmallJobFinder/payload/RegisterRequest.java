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
     * password requirements:
     * 8-30 characters, min 1 lowercase, min 1 uppercase, min 1 special character, min 1 digit, no whitespace
     * username requirements: 5-25 characters
     */

    @NotNull
    @NotEmpty
    @Email
    private String email;
    @NotNull(message = "Username is mandatory")
    @NotBlank(message = "Username is mandatory")
    @Length(min = 5, max = 25, message = "Username must have 5-25 characters")
    private String username;
    @ValidPassword
    private String password;
}
