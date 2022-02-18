package com.pentagon.cafe.virtualSmallJobFinder.controllers;

import com.pentagon.cafe.virtualSmallJobFinder.services.AuthenticationService;
import com.pentagon.cafe.virtualSmallJobFinder.services.UserService;
import com.pentagon.cafe.virtualSmallJobFinder.payload.LoginRequest;
import com.pentagon.cafe.virtualSmallJobFinder.payload.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @GetMapping("/hello")
    public ResponseEntity<String> helloWorld(){
        return ResponseEntity.ok("Hello world!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authenticationService.authenticateLoginRequest(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.status(201).body("User registered successfully with id:" + userService.addUser(registerRequest));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.status(201).body("User registered successfully with id:" + userService.addUser(registerRequest));
    }

}

