package com.cmc.invitaservice.models.external.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ChangePasswordRequest {
    @NotBlank
    private String oldPassword;
    @NotBlank
    private String newPassword;
    @NotBlank
    private String retypeNewPassword;
}
