package com.cmc.invitaservice.service.implement;

import com.cmc.invitaservice.repositories.RefreshTokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Date;

@Service
@Transactional
public class TokensPurgeTask {

    private final RefreshTokenRepository refreshTokenRepository;

    public TokensPurgeTask(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Scheduled(cron = "0 0 * * * ?")
    public void purgeExpired(){
        Date now = Date.from(Instant.now());
        refreshTokenRepository.deleteByExpiryDateLessThan(now);
    }
}
