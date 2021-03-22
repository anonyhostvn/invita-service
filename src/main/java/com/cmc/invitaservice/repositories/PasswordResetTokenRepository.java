package com.cmc.invitaservice.repositories;

import com.cmc.invitaservice.repositories.entities.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Date;


public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
    @Transactional
    void deletePasswordResetTokensByExpiryDateLessThan(Date date);
}
