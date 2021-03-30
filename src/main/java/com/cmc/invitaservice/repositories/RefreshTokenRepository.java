package com.cmc.invitaservice.repositories;

import com.cmc.invitaservice.repositories.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Date;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findByToken(String token);
    RefreshToken findByUsername(String username);
    @Transactional
    void deleteByUsername(String username);
    @Transactional
    void deleteByExpiryDateLessThan(Date date);
}
