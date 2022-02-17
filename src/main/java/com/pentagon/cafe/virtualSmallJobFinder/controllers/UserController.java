package com.pentagon.cafe.virtualSmallJobFinder.controllers;

import com.pentagon.cafe.virtualSmallJobFinder.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController("/api/users")
@AllArgsConstructor

public class UserController {

    private  final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/give/{id}/admin")
    public ResponseEntity<?> giveAdminRoleToUser(@PathVariable Long id){
        userService.giveAdminRoleToUser(id);
        return ResponseEntity.status(204).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/take/admin")
    public ResponseEntity<?> takeAdminRoleFromUser(@PathVariable Long id){
        userService.takeAdminRoleFromUser(id);
        return ResponseEntity.status(204).build();
    }

}
