package com.pentagon.cafe.virtualSmallJobFinder.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TokenRefreshRequest {
    @NotNull
    @NotBlank(message = "refresh token must not be blank")
    private String refreshToken;
}
