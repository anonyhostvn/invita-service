package com.cmc.invitaservice.cache;

import com.cmc.invitaservice.cache.entities.VerifyUserToken;
import com.cmc.invitaservice.models.external.request.CreateAccountRequest;

public interface VerifyUserTokenRepository {
    VerifyUserToken getByToken(String token);
    void deleteByToken(String token);
    VerifyUserToken addToken(String token, CreateAccountRequest createAccountRequest);
}
