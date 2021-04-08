package com.cmc.invitaservice.cache.service;

import com.cmc.invitaservice.cache.PasswordResetTokenRepository;
import com.cmc.invitaservice.cache.entities.PasswordResetToken;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service
public class PasswordResetTokenRepositoryImplement implements PasswordResetTokenRepository {

    @Override
    @Cacheable(value = "password_reset_token", key = "#token")
    public PasswordResetToken getPasswordResetTokenByToken(String token) {
        return null;
    }

    @Override
    @Cacheable(value = "password_reset_token", key = "#token")
    public PasswordResetToken addPasswordResetToken(String token, Long userId) {
        return new PasswordResetToken(token, userId);
    }

    @Override
    @CacheEvict(value = "password_reset_token", key = "#token")
    public void deletePasswordResetTokenByToken(String token) {
    }

}
