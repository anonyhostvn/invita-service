package com.cmc.invitaservice.cache.service;

import com.cmc.invitaservice.cache.VerifyUserTokenRepository;
import com.cmc.invitaservice.cache.entities.VerifyUserToken;
import com.cmc.invitaservice.models.external.request.CreateAccountRequest;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class VerifyUserTokenRepositoryImplement implements VerifyUserTokenRepository {

    @Override
    @Cacheable(value = "verify_user_token", key = "#token")
    public VerifyUserToken getVerifyUserTokenByToken(String token) {
        return null;
    }

    @Override
    @Cacheable(value = "verify_user_token", key = "#token")
    public VerifyUserToken addVerifyUserToken(String token, CreateAccountRequest createAccountRequest) {
        return new VerifyUserToken(token, createAccountRequest);
    }

    @Override
    @CacheEvict(value = "verify_user_token", key = "#token")
    public void deleteVerifyUserTokenByToken(String token) {
    }
}
