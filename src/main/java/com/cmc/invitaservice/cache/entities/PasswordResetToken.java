package com.cmc.invitaservice.cache.entities;

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

    private String token;
    private Long userId;
}
