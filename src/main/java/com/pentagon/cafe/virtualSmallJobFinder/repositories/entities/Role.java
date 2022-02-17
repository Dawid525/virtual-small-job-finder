package com.pentagon.cafe.virtualSmallJobFinder.repositories.entities;

import com.pentagon.cafe.virtualSmallJobFinder.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private RoleEnum name;


}
