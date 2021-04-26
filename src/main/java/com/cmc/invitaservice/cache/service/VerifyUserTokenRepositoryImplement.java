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
    public VerifyUserToken getByToken(String token) {
        return null;
    }

    @Override
    @Cacheable(value = "verify_user_token", key = "#token")
    public VerifyUserToken addToken(String token, CreateAccountRequest createAccountRequest) {
        return new VerifyUserToken(createAccountRequest);
    }

    @Override
    @CacheEvict(value = "verify_user_token", key = "#token")
    public void deleteByToken(String token) {
    }
}
