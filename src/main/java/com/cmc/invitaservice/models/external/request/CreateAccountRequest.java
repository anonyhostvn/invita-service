package com.cmc.invitaservice.models.external.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateAccountRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String retypePassword;
    @NotBlank
    private  String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private  String email;

}
