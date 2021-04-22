package com.cmc.invitaservice.actuator;

import com.cmc.invitaservice.cache.PasswordResetTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
public class ServiceHealthIndicator implements HealthIndicator {

    private final CacheManager cacheManager;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public ServiceHealthIndicator(CacheManager cacheManager, PasswordResetTokenRepository passwordResetTokenRepository) {
        this.cacheManager = cacheManager;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    @Override
    public Health health(){
        CaffeineCache cache = (CaffeineCache) cacheManager.getCache("password_reset_token");
        if (cache == null) {
            log.warn("Cache not available");
            return Health.down().withDetail("smoke test", "cache not available").build();
        }
        Set<Object> keys = cache.getNativeCache().asMap().keySet();
        for (Object key : keys){
            System.out.println(key.toString());
            System.out.println(passwordResetTokenRepository.getPasswordResetTokenByToken(key.toString()).getUserId());
        }
        //System.out.println(keys);
        return Health.up().build();
    }
}
