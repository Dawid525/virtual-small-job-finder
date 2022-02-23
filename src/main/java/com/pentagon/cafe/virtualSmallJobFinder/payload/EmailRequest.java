package com.pentagon.cafe.virtualSmallJobFinder.payload;

import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class EmailRequest {

    @NotNull(message = "Email is mandatory")
    @Email
    private String email;
}
