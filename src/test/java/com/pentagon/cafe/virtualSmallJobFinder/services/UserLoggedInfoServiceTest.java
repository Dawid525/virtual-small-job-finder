package com.pentagon.cafe.virtualSmallJobFinder.services;


import com.pentagon.cafe.virtualSmallJobFinder.enums.UserType;
import com.pentagon.cafe.virtualSmallJobFinder.repositories.UserRepository;
import com.pentagon.cafe.virtualSmallJobFinder.repositories.entities.UserEntity;
import com.pentagon.cafe.virtualSmallJobFinder.utils.UserDetailsImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserLoggedInfoServiceTest {


    @Mock
    private UserRepository userRepository;

    @Test
    void shouldGetLoggedUser(){
        //given
        UserLoggedInfoService userLoggedInfoService = new UserLoggedInfoService(userRepository);
        String username = "User";
        String password = "pswd";
        String email = "email@wp.pl";
        Long id = 1L;
        UserEntity loggedUser = UserEntity.builder()
                .id(id)
                .username(username)
                .password(password)
                .enabled(true)
                .type(UserType.EMPLOYEE)
                .email(email)
                .build();
        UserDetailsImpl userDetails = new UserDetailsImpl(id, email, username, password, UserType.EMPLOYEE, Collections.emptyList());
        //when
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null,null));
        when(userRepository.getByUsername(username)).thenReturn(loggedUser);
        UserEntity resultUser = userLoggedInfoService.getLoggedUser();
        //then
        assertThat(loggedUser).isEqualTo(resultUser);
    }








}
