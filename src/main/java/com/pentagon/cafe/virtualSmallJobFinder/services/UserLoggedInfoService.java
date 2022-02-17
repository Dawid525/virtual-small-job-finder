package com.pentagon.cafe.virtualSmallJobFinder.services;


import com.pentagon.cafe.virtualSmallJobFinder.repositories.UserRepository;
import com.pentagon.cafe.virtualSmallJobFinder.repositories.entities.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserLoggedInfoService {

    private final UserRepository userRepository;
    public UserEntity getLoggedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        String username = userDetails.getUsername();
        return userRepository.getByUsername(username);
    }

}
