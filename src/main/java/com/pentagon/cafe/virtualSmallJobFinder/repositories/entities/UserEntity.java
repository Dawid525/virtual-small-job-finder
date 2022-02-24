package com.pentagon.cafe.virtualSmallJobFinder.repositories.entities;


import com.pentagon.cafe.virtualSmallJobFinder.enums.UserType;
import lombok.*;
import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String username;
    private String password;
    @ManyToMany()
    @JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private Set<Role> roles = new HashSet<>();
    @Enumerated(EnumType.STRING)
    private UserType type;
    private boolean enabled;

}
