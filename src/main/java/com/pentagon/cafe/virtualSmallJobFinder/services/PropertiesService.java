package com.pentagon.cafe.virtualSmallJobFinder.services;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
public class PropertiesService {

    @Value("${app.jwtRefreshExpirationTimeMs}")
    private Long refreshTokenDurationMs;
}
