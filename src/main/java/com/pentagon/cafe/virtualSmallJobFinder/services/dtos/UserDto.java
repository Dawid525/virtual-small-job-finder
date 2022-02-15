package com.pentagon.cafe.virtualSmallJobFinder.services.dtos;

import com.pentagon.cafe.virtualSmallJobFinder.repositories.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String email;
    private String username;
    private String password;
    private Set<Role> roles = new HashSet<>();


}