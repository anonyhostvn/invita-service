package com.cmc.invitaservice.models.external.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ResetPasswordRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String email;
}
