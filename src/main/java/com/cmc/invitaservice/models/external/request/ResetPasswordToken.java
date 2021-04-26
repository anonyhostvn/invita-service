package com.cmc.invitaservice.models.external.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class ResetPasswordToken {
    @NotBlank
    private Long userId;
}
