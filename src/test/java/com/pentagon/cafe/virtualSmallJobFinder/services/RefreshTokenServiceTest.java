package com.pentagon.cafe.virtualSmallJobFinder.services;


import com.pentagon.cafe.virtualSmallJobFinder.exceptions.TokenRefreshException;
import com.pentagon.cafe.virtualSmallJobFinder.repositories.RefreshTokenRepository;
import com.pentagon.cafe.virtualSmallJobFinder.repositories.entities.RefreshToken;
import com.pentagon.cafe.virtualSmallJobFinder.repositories.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RefreshTokenServiceTest {

    private final RefreshTokenService refreshTokenService;
    private final UserService userService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PropertiesService propertiesService;
    private final long JWT_REFRESH_EXPIRATION_TIME_MS = 86400000L;


    public RefreshTokenServiceTest(){
        userService = mock(UserService.class);
        refreshTokenRepository = mock(RefreshTokenRepository.class);
        propertiesService = mock(PropertiesService.class);
        refreshTokenService = new RefreshTokenService(refreshTokenRepository, userService, propertiesService);
    }


    @Test
    void shouldReturnNotNullRefreshToken(){
        //given
        Long userId = 1L;
        UserEntity userEntity = UserEntity.builder()
                .username("user")
                .password("password")
                .email("email@wp.pl")
                .enabled(true)
                .roles(Collections.emptySet())
                .id(userId)
                .build();
        RefreshToken tokenToReturn = new RefreshToken();
        tokenToReturn.setUser(userEntity);
        tokenToReturn.setId(2L);
        tokenToReturn.setToken(UUID.randomUUID().toString());
        tokenToReturn.setExpiryDate(LocalDateTime.now().plusSeconds(JWT_REFRESH_EXPIRATION_TIME_MS));
        //when
        when(propertiesService.getRefreshTokenDurationMs()).thenReturn(JWT_REFRESH_EXPIRATION_TIME_MS);
        when(userService.getUserEntityById(any()))
                .thenReturn(userEntity);

        when(refreshTokenRepository.save(Mockito.any(RefreshToken.class)))
                .thenReturn(tokenToReturn);

        RefreshToken returnedToken = refreshTokenService.createRefreshToken(userId);
        //then
        verify(refreshTokenRepository, times(1)).save(any(RefreshToken.class));
        assertThat(returnedToken).isNotNull();
        assertThat(returnedToken).isEqualTo(tokenToReturn);
    }

    @Test
    void verifyDeleteMethod(){
        Long userId = 1L;
        UserEntity userEntity = UserEntity.builder()
                .username("user")
                .password("password")
                .email("email@wp.pl")
                .enabled(true)
                .roles(Collections.emptySet())
                .id(userId)
                .build();
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userEntity);
        refreshToken.setId(2L);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(LocalDateTime.now().plusSeconds(JWT_REFRESH_EXPIRATION_TIME_MS));
        //when
        refreshTokenService.deleteByUserId(userId);
        //then
        verify(refreshTokenRepository, times(1)).deleteByUser(any());
    }
    @Test
    void shouldThrowTokenRefreshException(){
        Long userId = 1L;
        UserEntity userEntity = UserEntity.builder()
                .username("user")
                .password("password")
                .email("email@wp.pl")
                .enabled(true)
                .roles(Collections.emptySet())
                .id(userId)
                .build();
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userEntity);
        refreshToken.setId(2L);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(LocalDateTime.now().plusSeconds(JWT_REFRESH_EXPIRATION_TIME_MS));
        String token = refreshToken.getToken();
        //when
        when(refreshTokenRepository.findByToken(token)).thenReturn(Optional.empty());
        //then
        assertThrows(TokenRefreshException.class, () -> refreshTokenService.findByToken(token));
    }
    @Test
    void shouldReturnRereshToken(){
        Long userId = 1L;
        UserEntity userEntity = UserEntity.builder()
                .username("user")
                .password("password")
                .email("email@wp.pl")
                .enabled(true)
                .roles(Collections.emptySet())
                .id(userId)
                .build();
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userEntity);
        refreshToken.setId(2L);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(LocalDateTime.now().plusSeconds(JWT_REFRESH_EXPIRATION_TIME_MS));
        String token = refreshToken.getToken();
        //when
        when(refreshTokenRepository.findByToken(token)).thenReturn(Optional.of(refreshToken));
        var result = refreshTokenService.findByToken(token);
        //then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(refreshToken);
    }
    @Test
    void verifyExpirationWithoutAnyExceptions() {
        //given
        Long userId = 1L;
        UserEntity userEntity = UserEntity.builder()
                .username("user")
                .password("password")
                .email("email@wp.pl")
                .enabled(true)
                .roles(Collections.emptySet())
                .id(userId)
                .build();
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userEntity);
        refreshToken.setId(2L);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(LocalDateTime.now().plusSeconds(JWT_REFRESH_EXPIRATION_TIME_MS));
        //when
        refreshTokenService.verifyExpiration(refreshToken);
    }

    @Test
    void shouldThrowRefreshTokenException() {
            //given
            Long userId = 1L;
            UserEntity userEntity = UserEntity.builder()
                    .username("user")
                    .password("password")
                    .email("email@wp.pl")
                    .enabled(true)
                    .roles(Collections.emptySet())
                    .id(userId)
                    .build();
            RefreshToken refreshToken = new RefreshToken();
            refreshToken.setUser(userEntity);
            refreshToken.setId(2L);
            refreshToken.setToken(UUID.randomUUID().toString());
            refreshToken.setExpiryDate(LocalDateTime.now().minusSeconds(JWT_REFRESH_EXPIRATION_TIME_MS));
            //when
            //then
            assertThrows(TokenRefreshException.class, () -> refreshTokenService.verifyExpiration(refreshToken));
    }
}


