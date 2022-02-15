package com.pentagon.cafe.virtualSmallJobFinder.services;



import com.pentagon.cafe.virtualSmallJobFinder.enums.RoleEnum;
import com.pentagon.cafe.virtualSmallJobFinder.exceptions.RoleNotFoundException;
import com.pentagon.cafe.virtualSmallJobFinder.exceptions.UserWithThisEmailExistsException;
import com.pentagon.cafe.virtualSmallJobFinder.exceptions.UserWithThisUsernameExistsException;
import com.pentagon.cafe.virtualSmallJobFinder.repositories.RoleRepository;
import com.pentagon.cafe.virtualSmallJobFinder.repositories.UserRepository;
import com.pentagon.cafe.virtualSmallJobFinder.repositories.entities.Role;
import com.pentagon.cafe.virtualSmallJobFinder.repositories.entities.UserEntity;
import com.pentagon.cafe.virtualSmallJobFinder.utils.RegisterRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    public Long addUser(RegisterRequest registerRequest){
        Set<Role> roles = new HashSet<>();
        if(userRepository.existsByEmail(registerRequest.getEmail())){
            throw new UserWithThisEmailExistsException(registerRequest.getEmail());
        }
        else if(userRepository.existsByUsername(registerRequest.getUsername())){
            throw new UserWithThisUsernameExistsException(registerRequest.getUsername());
        }
        var requestRoles = registerRequest.getRoles();
        if( requestRoles == null || requestRoles.isEmpty() ){
            roles.add(roleRepository.getByName(RoleEnum.ROLE_USER));
        }
        else{
            requestRoles
                    .forEach((role) -> roles.add(roleRepository.findByName(RoleEnum.valueOf("ROLE_"+ role.toUpperCase(Locale.ROOT)))
                            .orElseThrow(RoleNotFoundException::new)));
        }
        UserEntity user = UserEntity.builder()
                .email(registerRequest.getEmail())
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(roles)
                .build();

        return userRepository.save(user).getId();
    }
}
