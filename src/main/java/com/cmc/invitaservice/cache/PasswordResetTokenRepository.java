package com.cmc.invitaservice.cache;

import com.cmc.invitaservice.cache.entities.PasswordResetToken;

public interface PasswordResetTokenRepository{
    PasswordResetToken getPasswordResetTokenByToken(String token);
    void deletePasswordResetTokenByToken(String token);
    PasswordResetToken addPasswordResetToken(String token, Long userId);
}
