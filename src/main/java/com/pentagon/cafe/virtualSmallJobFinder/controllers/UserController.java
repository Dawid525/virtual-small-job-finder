package com.pentagon.cafe.virtualSmallJobFinder.controllers;

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
    @PostMapping("/{id}/give/admin")
    public ResponseEntity<?> giveAdminRoleToUser(@PathVariable Long id){
        userService.giveAdminRoleToUser(id);
        return ResponseEntity.status(204).build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/{id}/take/admin")
    public ResponseEntity<?> takeAdminRoleFromUser(@PathVariable Long id){
        userService.takeAdminRoleFromUser(id);
        return ResponseEntity.status(204).build();
    }

    @PutMapping()
    public ResponseEntity<?> changeEmailByLoggedUser(@RequestBody UserDto userDto){
        userService.updateUser(userDto);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/change/password")
    public ResponseEntity<?> changePasswordByLoggedUser(@RequestBody UserDto userDto){
        userService.changePassword(userDto);
        return ResponseEntity.status(204).build();
    }


}
