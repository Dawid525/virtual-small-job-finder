package com.pentagon.cafe.virtualSmallJobFinder.controllers;

import com.pentagon.cafe.virtualSmallJobFinder.payload.*;
import com.pentagon.cafe.virtualSmallJobFinder.repositories.entities.UserEntity;
import com.pentagon.cafe.virtualSmallJobFinder.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping()
    public ResponseEntity<List<UserEntity>> getAllUsersByAdmin() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{username}/give/admin")
    public ResponseEntity<?> giveAdminRoleToUserByAdmin(@PathVariable String username) {
        userService.giveAdminRoleToUser(username);
        return ResponseEntity.status(204).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{username}/take/admin")
    public ResponseEntity<?> takeAdminRoleFromUserByAdmin(@PathVariable String username) {
        userService.takeAdminRoleFromUser(username);
        return ResponseEntity.status(204).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping()
    public ResponseEntity<?> addUser(@Valid @RequestBody RegisterRequest registerRequest) {
        userService.addUser(registerRequest);
        return ResponseEntity.status(204).build();
    }

    @GetMapping("/checkUsername")
    public ResponseEntity<Boolean> isUsernameAvailable(@Valid @RequestBody UsernameRequest usernameRequest) {
        return ResponseEntity.ok(userService.isUsernameAvailable(usernameRequest.getUsername()));
    }

    @GetMapping("/checkEmail")
    public ResponseEntity<Boolean> isEmailAvailable(@Valid @RequestBody EmailRequest emailRequest) {
        return ResponseEntity.ok(userService.isEmailAvailable(emailRequest.getEmail()));
    }

    @PutMapping("/change/email")
    public ResponseEntity<?> changeEmailByLoggedInUser(@Valid @RequestBody EmailRequest emailRequest) {
        userService.changeEmail(emailRequest.getEmail());
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/change/password")
    public ResponseEntity<?> changePasswordByLoggedInUser(@Valid @RequestBody PasswordRequest passwordRequest) {
        userService.changePassword(passwordRequest.getPassword());
        return ResponseEntity.status(204).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{username}/enable/user")
    public ResponseEntity<?> enableUserByAdmin(@PathVariable String username) {
        userService.enableUserByUsername(username);
        return ResponseEntity.status(204).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{username}/disable/user")
    public ResponseEntity<?> disableUserByAdmin(@PathVariable String username) {
        userService.disableUserByUsername(username);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/disable/user")
    public ResponseEntity<?> disableUserByLoggedInUser() {
        userService.disableUser();
        return ResponseEntity.status(204).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{username}/enable/user")
    public ResponseEntity<?> disableUser(@PathVariable String username){
        userService.enableUserByUsername(username);
        return ResponseEntity.status(204).build();
    }


    @PutMapping("/change/type")
    public ResponseEntity<?> changeType(@Valid @RequestBody TypeRequest typeRequest) {
        userService.changeType(typeRequest.getType());
        return ResponseEntity.status(204).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping()
    public ResponseEntity<?> deleteUserByAdmin(@Valid @RequestBody UsernameRequest usernameRequest) {
        userService.deleteUserByUsername(usernameRequest.getUsername());
        return ResponseEntity.status(200).build();
    }
}
