package com.pentagon.cafe.virtualSmallJobFinder.payload;

import lombok.Data;

@Data
public class TokenRefreshRequest {
    //@NotBlank
    private String refreshToken;
}
