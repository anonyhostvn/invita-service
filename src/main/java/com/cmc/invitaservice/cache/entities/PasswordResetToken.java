package com.cmc.invitaservice.cache.entities;

import com.cmc.invitaservice.models.external.request.ResetPasswordToken;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetToken{
    private static final long serialVersionUID = -1088727601152369294L;

    private Long userId;

    public PasswordResetToken(ResetPasswordToken resetPasswordToken){
        this.userId =  resetPasswordToken.getUserId();
    }
}
