package com.pentagon.cafe.virtualSmallJobFinder.utils;

import com.pentagon.cafe.virtualSmallJobFinder.enums.UserType;
import com.pentagon.cafe.virtualSmallJobFinder.repositories.entities.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {



    private Long id;
    private String email;
    private String username;
    private String password;
    private UserType type;
    private Collection<? extends GrantedAuthority> authorities;


    public static UserDetails build(UserEntity userEntity){
        var authorities = userEntity.getRoles().stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
        return new UserDetailsImpl(userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getUsername(),
                userEntity.getPassword(), userEntity.getType(),
                authorities);
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
