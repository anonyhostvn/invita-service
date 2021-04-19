package com.cmc.invitaservice.models.external.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@NotBlank
public class UpdateAccountRequest {
    private String username;
    private String password;
    private  String firstName;
    private String lastName;
    private  String email;
}
