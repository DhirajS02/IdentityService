package com.example.identityservice.repository;

import com.example.identityservice.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    RefreshToken findByRefreshToken(String refreshToken);

}
