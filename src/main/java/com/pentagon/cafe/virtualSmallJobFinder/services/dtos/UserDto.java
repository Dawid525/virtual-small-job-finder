package com.pentagon.cafe.virtualSmallJobFinder.services.dtos;

import com.pentagon.cafe.virtualSmallJobFinder.enums.UserType;
import com.pentagon.cafe.virtualSmallJobFinder.repositories.entities.Role;
import com.pentagon.cafe.virtualSmallJobFinder.validation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    @NotNull(message = "email is mandatory")
    @NotEmpty
    @Email
    private String email;
    @NotNull(message = "Username is mandatory")
    @NotBlank
    @Length(min = 5, max = 25, message = "Username must have 5-25 characters")
    private String username;
    @NotNull(message = "Password is mandatory")
    @NotBlank
    @ValidPassword
    private String password;
    private Set<Role> roles = new HashSet<>();
    @NotEmpty
    private UserType type;
    private boolean enabled;

}

