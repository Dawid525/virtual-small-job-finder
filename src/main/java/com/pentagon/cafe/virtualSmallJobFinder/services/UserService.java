package com.pentagon.cafe.virtualSmallJobFinder.services;


import com.pentagon.cafe.virtualSmallJobFinder.enums.ErrorMessageEnum;
import com.pentagon.cafe.virtualSmallJobFinder.enums.RoleEnum;
import com.pentagon.cafe.virtualSmallJobFinder.enums.UserType;
import com.pentagon.cafe.virtualSmallJobFinder.exceptions.UserNotFoundException;
import com.pentagon.cafe.virtualSmallJobFinder.exceptions.UserWithThisEmailExistsException;
import com.pentagon.cafe.virtualSmallJobFinder.exceptions.UserWithThisUsernameExistsException;
import com.pentagon.cafe.virtualSmallJobFinder.payload.RegisterRequest;
import com.pentagon.cafe.virtualSmallJobFinder.repositories.RoleRepository;
import com.pentagon.cafe.virtualSmallJobFinder.repositories.UserRepository;
import com.pentagon.cafe.virtualSmallJobFinder.repositories.entities.Role;
import com.pentagon.cafe.virtualSmallJobFinder.repositories.entities.UserEntity;
import com.pentagon.cafe.virtualSmallJobFinder.services.dtos.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserLoggedInfoService userLoggedInfoService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;



    public void addUser(RegisterRequest registerRequest) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.getByName(RoleEnum.ROLE_USER));
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new UserWithThisEmailExistsException(registerRequest.getEmail() + ErrorMessageEnum.EMAIL_IS_NOT_AVAILABLE.getMessage());
        } else if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new UserWithThisUsernameExistsException(registerRequest.getUsername() + ErrorMessageEnum.USERNAME_IS_NOT_AVAILABLE.getMessage());
        }
        UserEntity user = UserEntity.builder()
                .email(registerRequest.getEmail())
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(roles)
                .type(registerRequest.getType())
                .enabled(true)
                .build();
        userRepository.save(user);
    }



    private UserEntity getUserEntityByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(ErrorMessageEnum.NOT_FOUND_USERNAME.getMessage() + username));
    }

    public UserEntity getUserEntityById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(ErrorMessageEnum.NOT_FOUND_USER.getMessage() + id));
    }


    @Transactional
    public void enableUserByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(ErrorMessageEnum.NOT_FOUND_USERNAME.getMessage() + username));
        userEntity.setEnabled(true);
    }

    @Transactional
    public void disableUserByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(ErrorMessageEnum.NOT_FOUND_USERNAME.getMessage() + username));
        userEntity.setEnabled(false);
    }
    @Transactional
    public void giveAdminRoleToUser(String username) {
        UserEntity userEntity = getUserEntityByUsername(username);
        Role admin = roleRepository.getByName(RoleEnum.ROLE_ADMIN);
        var roles = userEntity.getRoles();
        if (!roles.contains(admin)) {
            userEntity.getRoles().add(admin);
        }
    }

    @Transactional
    public void takeAdminRoleFromUser(String username) {
        UserEntity userEntity = getUserEntityByUsername(username);
        Role admin = roleRepository.getByName(RoleEnum.ROLE_ADMIN);
        var roles = userEntity.getRoles();
        if (roles.contains(admin)) {
            userEntity.getRoles().remove(admin);
        }
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void updateUser(@Valid UserDto userDto) {
        //TODO
    }

    @Transactional
    public void changePassword(String password) {
        UserEntity userEntity = userLoggedInfoService.getLoggedUser();
        if (Objects.nonNull(password)) {
            userEntity.setPassword(passwordEncoder.encode(password));
        }
    }

    @Transactional
    public void changeEmail(String email) {
        UserEntity userEntity = userLoggedInfoService.getLoggedUser();
        if (Objects.nonNull(email)) {
            userEntity.setEmail(email);
        }
    }

    @Transactional
    public void changeType(UserType type) {
        UserEntity userEntity = userLoggedInfoService.getLoggedUser();
        if (Objects.nonNull(type)) {
            userEntity.setType(type);
        }
    }

    @Transactional
    public void deleteUserByUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            userRepository.deleteByUsername(username);
        }
    }

    public boolean isEmailAvailable(String email) {
        return !userRepository.existsByEmail(email);
    }

    public boolean isUsernameAvailable(String username) {
        return !userRepository.existsByUsername(username);
    }

    @Transactional
    public void disableUser() {
        UserEntity userEntity = userLoggedInfoService.getLoggedUser();
        userEntity.setEnabled(false);
    }

}
