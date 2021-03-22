package com.cmc.invitaservice.repositories;

import com.cmc.invitaservice.repositories.entities.VerifyUserToken;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Date;

public interface VerifyUserTokenRepository extends JpaRepository<VerifyUserToken, Long> {
    VerifyUserToken findByToken(String token);
    @Transactional
    void deleteVerifyUserTokensByExpiryDateLessThan(Date now);
}
