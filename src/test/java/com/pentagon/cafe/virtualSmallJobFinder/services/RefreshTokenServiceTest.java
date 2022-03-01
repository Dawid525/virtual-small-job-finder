package com.pentagon.cafe.virtualSmallJobFinder.services;


import com.pentagon.cafe.virtualSmallJobFinder.repositories.RefreshTokenRepository;
import com.pentagon.cafe.virtualSmallJobFinder.repositories.entities.RefreshToken;
import com.pentagon.cafe.virtualSmallJobFinder.repositories.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RefreshTokenServiceTest {


   // @
  // @InjectMocks
   //@MockBean
  //  private UserService userService;
   // @Mock
 //  @InjectMocks
  //  private RefreshTokenRepository refreshTokenRepository;

   // private final RefreshTokenService refreshTokenService = new RefreshTokenService(refreshTokenRepository, userService, 86400000L);



    @Test
    void test(){
        //given
        UserService userService = mock(UserService.class);
        RefreshTokenRepository refreshTokenRepository = mock(RefreshTokenRepository.class);
        RefreshTokenService refreshTokenService = new RefreshTokenService(refreshTokenRepository, userService, 86400000L );
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
        tokenToReturn.setExpiryDate(LocalDateTime.now().plusSeconds(86400000));
        //when
        when(userService.getUserEntityById(any()))
                .thenReturn(userEntity);

        when(refreshTokenRepository.save(Mockito.any(RefreshToken.class)))
                .thenReturn(tokenToReturn);

        RefreshToken returnedToken = refreshTokenService.createRefreshToken(userId);
        //then
        assertThat(returnedToken).isNotNull();
    }

}


