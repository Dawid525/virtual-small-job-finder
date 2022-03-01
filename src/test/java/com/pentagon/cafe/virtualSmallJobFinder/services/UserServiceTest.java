package com.pentagon.cafe.virtualSmallJobFinder.services;


import com.pentagon.cafe.virtualSmallJobFinder.enums.RoleEnum;
import com.pentagon.cafe.virtualSmallJobFinder.enums.UserType;
import com.pentagon.cafe.virtualSmallJobFinder.exceptions.UserWithThisEmailExistsException;
import com.pentagon.cafe.virtualSmallJobFinder.exceptions.UserWithThisUsernameExistsException;
import com.pentagon.cafe.virtualSmallJobFinder.payload.RegisterRequest;
import com.pentagon.cafe.virtualSmallJobFinder.repositories.RoleRepository;
import com.pentagon.cafe.virtualSmallJobFinder.repositories.UserRepository;
import com.pentagon.cafe.virtualSmallJobFinder.repositories.entities.Role;
import com.pentagon.cafe.virtualSmallJobFinder.repositories.entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserLoggedInfoService userLoggedInfoService;
    @Mock
    private PasswordEncoder passwordEncoder;


    private UserService userService;

    @BeforeEach
    void init() {
        userService = new UserService(userRepository, userLoggedInfoService, roleRepository, passwordEncoder);

    }

    @Test
    void shouldReturnListOfUsers() {
        //given
        String username1 = "user1";
        String username2 = "user2";
        UserEntity user1 = UserEntity.builder()
                .username(username1)
                .roles(Collections.emptySet())
                .build();
        UserEntity user2 = UserEntity.builder()
                .username(username2)
                .roles(Collections.emptySet())
                .build();

        List<UserEntity> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        //when
        when(userRepository.findAll()).thenReturn(users);
        var result = userService.getAllUsers();
        //then
        assertThat(result).hasSize(2);

    }

    @Test
    void shouldReturnUserWithOneRoleAndRoleIsROLE_User() {
        //given
        String username = "user";
        Role userRole = new Role(1L, RoleEnum.ROLE_USER);
        Role adminRole = new Role(2L, RoleEnum.ROLE_ADMIN);
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        roles.add(adminRole);
        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .roles(roles)
                .build();
        //when
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(roleRepository.getByName(RoleEnum.ROLE_ADMIN)).thenReturn(adminRole);
        userService.takeAdminRoleFromUser(username);
        var result = userEntity.getRoles();
        //then
        assertThat(result).hasSize(1).contains(userRole);
    }

    @Test
    void shouldReturnUserWithTwoRoles() {
        //given
        String username = "user";
        Role userRole = new Role(1L, RoleEnum.ROLE_USER);
        Role adminRole = new Role(2L, RoleEnum.ROLE_ADMIN);
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .roles(roles)
                .build();
        //when
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(roleRepository.getByName(RoleEnum.ROLE_ADMIN)).thenReturn(adminRole);
        userService.giveAdminRoleToUser(username);
        var result = userEntity.getRoles();
        //then
        assertThat(result).hasSize(2).contains(userRole).contains(adminRole);

    }

    @Test
    void verifySaveMethodDuringAddedUser() {
        //given
        String username = "user";
        String password = "psswd";
        String email = "poczta@wp.pl";
        UserType userType = UserType.EMPLOYEE;
        Role role = new Role(1L, RoleEnum.ROLE_USER);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .password(password)
                .type(userType)
                .email(email)
                .enabled(true)
                .roles(roles)
                .build();
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(email);
        registerRequest.setUsername(username);
        registerRequest.setType(userType);
        registerRequest.setPassword(password);
        //when
        when(passwordEncoder.encode(password)).thenReturn(password);
        when(roleRepository.getByName(RoleEnum.ROLE_USER)).thenReturn(role);
        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(userRepository.existsByUsername(username)).thenReturn(false);
        userService.addUser(registerRequest);
        //then
        verify(userRepository, times(1)).save(userEntity);
    }

    @Test
    void shouldThrowUserWithThisUsernameExistsException() {
        //given
        String username = "user";
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername(username);
        //when
        when(userRepository.existsByUsername(username)).thenReturn(true);
        assertThrows(UserWithThisUsernameExistsException.class, () -> userService.addUser(registerRequest));
    }

    @Test
    void shouldThrowUserWithThisEmailExistsException() {
        //given
        String email = "poczta@wp.pl";
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(email);
        //when
        when(userRepository.existsByEmail(email)).thenReturn(true);
        assertThrows(UserWithThisEmailExistsException.class, () -> userService.addUser(registerRequest));
    }

    @Test
    void verifyDeleteMethod() {
        //given
        String username = "user";
        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .roles(Collections.emptySet())
                .build();
        //when
        when(userRepository.existsByUsername(username)).thenReturn(true);
        userService.deleteUserByUsername(username);
        verify(userRepository, times(1)).deleteByUsername(username);
    }


    @Test
    void shouldChangeEnabledToTrue() {
        //given
        String username = "user";
        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .enabled(false)
                .roles(Collections.emptySet())
                .build();
        //when
        when(userRepository.findByUsername(username)).thenReturn(java.util.Optional.ofNullable(userEntity));
        userService.enableUserByUsername(username);
        //then
        assertThat(userEntity.isEnabled()).isTrue();
    }

    @Test
    void shouldChangeEnabledToFalse() {
        //given
        String username = "user";
        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .enabled(true)
                .roles(Collections.emptySet())
                .build();
        //when
        when(userRepository.findByUsername(username)).thenReturn(java.util.Optional.ofNullable(userEntity));
        userService.disableUserByUsername(username);
        //then
        assertThat(userEntity.isEnabled()).isFalse();
    }

    @Test
    void shouldChangeEnabledToFalseByLoggedInUser() {
        //given
        String username = "user";
        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .enabled(true)
                .roles(Collections.emptySet())
                .build();
        //when
        when(userLoggedInfoService.getLoggedUser()).thenReturn(userEntity);
        userService.disableUser();
        //then
        assertThat(userEntity.isEnabled()).isFalse();
    }

    @Test
    void shouldChangePassword() {
        //given
        String username = "user";
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .password(oldPassword)
                .enabled(true)
                .roles(Collections.emptySet())
                .build();
        //when
        when(userLoggedInfoService.getLoggedUser()).thenReturn(userEntity);
        when(passwordEncoder.encode(newPassword)).thenReturn(newPassword);
        userService.changePassword(newPassword);
        //then
        assertThat(userEntity.getPassword()).isEqualTo(newPassword);
    }

    @Test
    void shouldChangeEmail() {
        //given
        String username = "user";
        String email = "email@gmail.com";
        String newEmail = "newEmail@gmail.com";
        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .email(email)
                .enabled(true)
                .roles(Collections.emptySet())
                .build();
        //when
        when(userLoggedInfoService.getLoggedUser()).thenReturn(userEntity);
        userService.changeEmail(newEmail);
        //then
        assertThat(userEntity.getEmail()).isEqualTo(newEmail);
    }

    @Test
    void shouldChangeType() {
        //given
        String username = "user";
        UserType type = UserType.EMPLOYER;
        UserType newType = UserType.EMPLOYEE;
        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .type(type)
                .enabled(true)
                .roles(Collections.emptySet())
                .build();
        //when
        when(userLoggedInfoService.getLoggedUser()).thenReturn(userEntity);
        userService.changeType(newType);
        //then
        assertThat(userEntity.getType()).isEqualTo(newType);
    }

    @Test
    void shouldReturnTrue() {
        //given
        String username = "user";
        String email = "email@gmail.com";
        //when
        when(userRepository.existsByEmail(email)).thenReturn(true);
        when(userRepository.existsByUsername(username)).thenReturn(true);
        var emailAvailability = userService.isEmailAvailable(email);
        var usernameAvailability = userService.isUsernameAvailable(username);
        //then
        assertThat(emailAvailability).isFalse();
        assertThat(usernameAvailability).isFalse();
    }


}
