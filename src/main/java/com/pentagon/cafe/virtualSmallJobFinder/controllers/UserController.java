package com.pentagon.cafe.virtualSmallJobFinder.controllers;

import com.pentagon.cafe.virtualSmallJobFinder.payload.RegisterRequest;
import com.pentagon.cafe.virtualSmallJobFinder.repositories.entities.UserEntity;
import com.pentagon.cafe.virtualSmallJobFinder.services.UserService;
import com.pentagon.cafe.virtualSmallJobFinder.services.dtos.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private  final UserService userService;


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping()
    public ResponseEntity<List<UserEntity>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{username}/give/admin")
    public ResponseEntity<?> giveAdminRoleToUser(@PathVariable String username){
        userService.giveAdminRoleToUser(username);
        return ResponseEntity.status(204).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{username}/take/admin")
    public ResponseEntity<?> takeAdminRoleFromUser(@PathVariable String username){
        userService.takeAdminRoleFromUser(username);
        return ResponseEntity.status(204).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping()
    public ResponseEntity<?> addUser(@RequestBody RegisterRequest registerRequest){
        userService.addUser(registerRequest);
        return ResponseEntity.status(204).build();
    }
    @GetMapping("/checkUsername")
    public ResponseEntity<Boolean> isUsernameAvailable(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(userService.isUsernameAvailable(registerRequest.getUsername()));
    }

    @GetMapping("/checkEmail")
    public ResponseEntity<Boolean> isEmailAvailable(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(userService.isEmailAvailable(registerRequest.getEmail()));
    }

    @PutMapping("/change/email")
    public ResponseEntity<?> changeEmailByLoggedUser(@RequestBody UserDto userDto){
        userService.changeEmail(userDto);
        return ResponseEntity.status(204).build();
    }
    @PutMapping("/change/password")
    public ResponseEntity<?> changePasswordByLoggedUser(@RequestBody UserDto userDto){
        userService.changePassword(userDto);
        return ResponseEntity.status(204).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username){
        userService.deleteUserByUsername(username);
        return ResponseEntity.status(200).build();
    }

}
