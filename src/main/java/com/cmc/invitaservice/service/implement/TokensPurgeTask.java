package com.cmc.invitaservice.service.implement;

import com.cmc.invitaservice.repositories.PasswordResetTokenRepository;
import com.cmc.invitaservice.repositories.VerifyUserTokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Date;

@Service
@Transactional
public class TokensPurgeTask {

    private final VerifyUserTokenRepository verifyUserTokenRepository;

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public TokensPurgeTask(VerifyUserTokenRepository verifyUserTokenRepository, PasswordResetTokenRepository passwordResetTokenRepository) {
        this.verifyUserTokenRepository = verifyUserTokenRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    @Scheduled(cron = "0 0 10 * * ?")
    public void purgeExpired(){
        Date now = Date.from(Instant.now());
        passwordResetTokenRepository.deletePasswordResetTokensByExpiryDateLessThan(now);
        verifyUserTokenRepository.deleteVerifyUserTokensByExpiryDateLessThan(now);
    }
}
