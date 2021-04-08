package com.cmc.invitaservice.cache;

import com.cmc.invitaservice.cache.entities.VerifyUserToken;
import com.cmc.invitaservice.models.external.request.CreateAccountRequest;

public interface VerifyUserTokenRepository {
    VerifyUserToken getVerifyUserTokenByToken(String token);
    void deleteVerifyUserTokenByToken(String token);
    VerifyUserToken addVerifyUserToken(String token, CreateAccountRequest createAccountRequest);
}
