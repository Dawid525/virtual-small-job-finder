package com.pentagon.cafe.virtualSmallJobFinder.services;

import com.pentagon.cafe.virtualSmallJobFinder.exceptions.TokenRefreshException;
import com.pentagon.cafe.virtualSmallJobFinder.repositories.RefreshTokenRepository;
import com.pentagon.cafe.virtualSmallJobFinder.repositories.entities.RefreshToken;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenService {

    @Autowired
    private  RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private  UserService userService;

    @Value("${app.jwtRefreshExpirationTimeMs}")
    private Long refreshTokenDurationMs;

    public RefreshToken createRefreshToken(Long userId) {

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userService.getUserEntityById(userId));
        refreshToken.setExpiryDate(LocalDateTime.now().plusSeconds(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());
        return refreshTokenRepository.save(refreshToken);
    }

    public void verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(LocalDateTime.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUser(userService.getUserEntityById(userId));
    }

    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token).orElseThrow(() -> new TokenRefreshException(token, "Refresh token is not in database!"));
    }
}
