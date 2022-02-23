package com.pentagon.cafe.virtualSmallJobFinder.payload;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UsernameRequest {

    @NotNull(message = "Username is mandatory")
    @NotBlank(message = "Username is mandatory")
    @Length(min = 5, max = 25, message = "Username must have 5-25 characters")
    private String username;

}
