package com.pentagon.cafe.virtualSmallJobFinder.services;



import com.pentagon.cafe.virtualSmallJobFinder.enums.ErrorMessageEnum;
import com.pentagon.cafe.virtualSmallJobFinder.enums.RoleEnum;
import com.pentagon.cafe.virtualSmallJobFinder.exceptions.UserWithThisEmailExistsException;
import com.pentagon.cafe.virtualSmallJobFinder.exceptions.UserWithThisUsernameExistsException;
import com.pentagon.cafe.virtualSmallJobFinder.repositories.RoleRepository;
import com.pentagon.cafe.virtualSmallJobFinder.repositories.UserRepository;
import com.pentagon.cafe.virtualSmallJobFinder.repositories.entities.Role;
import com.pentagon.cafe.virtualSmallJobFinder.repositories.entities.UserEntity;
import com.pentagon.cafe.virtualSmallJobFinder.services.dtos.UserDto;
import com.pentagon.cafe.virtualSmallJobFinder.payload.RegisterRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserLoggedInfoService userLoggedInfoService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public Long addUser(RegisterRequest registerRequest){
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.getByName(RoleEnum.ROLE_USER));
        if(userRepository.existsByEmail(registerRequest.getEmail())){
            throw new UserWithThisEmailExistsException(registerRequest.getEmail() + ErrorMessageEnum.EMAIL_IS_NOT_AVAILABLE.getMessage());
        }
        else if(userRepository.existsByUsername(registerRequest.getUsername())){
            throw new UserWithThisUsernameExistsException(registerRequest.getUsername() + ErrorMessageEnum.USERNAME_IS_NOT_AVAILABLE.getMessage());
        }
        UserEntity user = UserEntity.builder()
                .email(registerRequest.getEmail())
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(roles)
                .build();
        return userRepository.save(user).getId();
    }

    @Transactional
    public void giveAdminRoleToUser(String username) {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(ErrorMessageEnum.NOT_FOUND_USERNAME.getMessage() + username));
        Role admin = roleRepository.getByName(RoleEnum.ROLE_ADMIN);
        var roles = userEntity.getRoles();
        if(!roles.contains(admin)){
           userEntity.getRoles().add(admin);
        }
    }

    @Transactional
    public void takeAdminRoleFromUser(String username) {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(ErrorMessageEnum.NOT_FOUND_USERNAME.getMessage() + username));
        Role admin = roleRepository.getByName(RoleEnum.ROLE_ADMIN);
        var roles = userEntity.getRoles();
        if(roles.contains(admin)){
            userEntity.getRoles().remove(admin);
        }
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void updateUser(UserDto userDto){
            //TODO
    }

    @Transactional
    public void changePassword(UserDto userDto) {
        UserEntity userEntity = userLoggedInfoService.getLoggedUser();
        if(Objects.nonNull(userDto.getPassword())) {
            userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
    }

    @Transactional
    public void changeEmail(UserDto userDto) {
        UserEntity userEntity = userLoggedInfoService.getLoggedUser();
        if(Objects.nonNull(userDto.getEmail())){
            userEntity.setEmail(userDto.getEmail());
        }
    }

    @Transactional
    public void deleteUserByUsername(String username) {
        if(userRepository.existsByUsername(username)){
            userRepository.deleteByUsername(username);
        }
    }

    public boolean isEmailAvailable(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean isUsernameAvailable(String username) {
        return userRepository.existsByUsername(username);
    }
}
