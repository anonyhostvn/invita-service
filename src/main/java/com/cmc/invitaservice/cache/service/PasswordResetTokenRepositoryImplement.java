package com.cmc.invitaservice.cache.service;

import com.cmc.invitaservice.cache.PasswordResetTokenRepository;
import com.cmc.invitaservice.cache.entities.PasswordResetToken;
import com.cmc.invitaservice.models.external.request.ResetPasswordToken;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service
public class PasswordResetTokenRepositoryImplement implements PasswordResetTokenRepository {

    @Override
    @Cacheable(cacheNames = "password_reset_token", key = "#token")
    public PasswordResetToken getByToken(String token) {
        return null;
    }

    @Override
    @Cacheable(cacheNames = "password_reset_token", key = "#token")
    public PasswordResetToken addToken(String token, ResetPasswordToken resetPasswordToken) {
        return new PasswordResetToken(resetPasswordToken);
    }

    @Override
    @CacheEvict(cacheNames = "password_reset_token", key = "#token")
    public void deleteByToken(String token) {
    }
}
