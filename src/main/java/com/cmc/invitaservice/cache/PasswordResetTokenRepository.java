package com.cmc.invitaservice.cache;

import com.cmc.invitaservice.cache.entities.PasswordResetToken;
import com.cmc.invitaservice.models.external.request.ResetPasswordToken;

public interface PasswordResetTokenRepository {
    PasswordResetToken getByToken(String token);
    void deleteByToken(String token);
    PasswordResetToken addToken(String token, ResetPasswordToken resetPasswordToken);
}
