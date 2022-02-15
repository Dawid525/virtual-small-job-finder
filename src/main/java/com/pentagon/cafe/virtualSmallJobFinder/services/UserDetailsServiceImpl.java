package com.pentagon.cafe.virtualSmallJobFinder.services;


import com.pentagon.cafe.virtualSmallJobFinder.repositories.UserRepository;
import com.pentagon.cafe.virtualSmallJobFinder.repositories.entities.UserEntity;
import com.pentagon.cafe.virtualSmallJobFinder.utils.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username){
               UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("username:" + " not found"));
        return UserDetailsImpl.build(userEntity);
    }
}
