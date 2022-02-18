package com.pentagon.cafe.virtualSmallJobFinder.repositories;

import com.pentagon.cafe.virtualSmallJobFinder.repositories.entities.RefreshToken;
import com.pentagon.cafe.virtualSmallJobFinder.repositories.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findById(Long id);
    Optional<RefreshToken> findByToken(String token);
    int deleteByUser(UserEntity userEntity);
}
