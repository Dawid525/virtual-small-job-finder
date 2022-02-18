package com.pentagon.cafe.virtualSmallJobFinder.repositories.entities;


import lombok.Data;
import org.apache.tomcat.jni.Local;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "refreshtoken")
@Data
public class RefreshToken {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;
        @OneToOne
        @JoinColumn(name = "user_id", referencedColumnName = "id")
        private UserEntity user;
        @Column(nullable = false, unique = true)
        private String token;
        @Column(nullable = false)
        private LocalDateTime expiryDate;
    }
