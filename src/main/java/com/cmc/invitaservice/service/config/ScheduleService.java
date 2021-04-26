package com.cmc.invitaservice.service.config;

import com.cmc.invitaservice.repositories.ApplicationUserRepository;
import com.cmc.invitaservice.repositories.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Date;

@Service
@Transactional
public class ScheduleService {

    @Value("${token.ttl}")
    private Long ttl;

    private final RefreshTokenRepository refreshTokenRepository;
    private final ApplicationUserRepository applicationUserRepository;

    public ScheduleService(RefreshTokenRepository refreshTokenRepository, ApplicationUserRepository applicationUserRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.applicationUserRepository = applicationUserRepository;
    }

    @Scheduled(cron = "0 0/5 * * * ?")
    public void purgeExpired(){
        Date now = Date.from(Instant.now());
        refreshTokenRepository.deleteByExpiryDateLessThan(now);
        now.setTime(now.getTime() - ttl * 1000);
        applicationUserRepository.deleteApplicationUserByStatusFalseAndCreatedTimeLessThan(now);
    }
}
