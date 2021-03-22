package com.cmc.invitaservice.repositories;

import com.cmc.invitaservice.repositories.entities.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
}
