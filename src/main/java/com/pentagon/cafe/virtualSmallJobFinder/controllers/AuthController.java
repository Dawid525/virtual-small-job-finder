package com.pentagon.cafe.virtualSmallJobFinder.controllers;

import com.pentagon.cafe.virtualSmallJobFinder.services.AuthenticationService;
import com.pentagon.cafe.virtualSmallJobFinder.services.UserService;
import com.pentagon.cafe.virtualSmallJobFinder.utils.JwtResponse;
import com.pentagon.cafe.virtualSmallJobFinder.utils.LoginRequest;
import com.pentagon.cafe.virtualSmallJobFinder.utils.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/login")
    public JwtResponse loginUser(@RequestBody LoginRequest loginRequest){
        return authenticationService.authenticateLoginRequest(loginRequest);
    }

    @PostMapping("/register")
    public Long registerUser(@RequestBody RegisterRequest registerRequest){
        return userService.addUser(registerRequest);
    }

}

