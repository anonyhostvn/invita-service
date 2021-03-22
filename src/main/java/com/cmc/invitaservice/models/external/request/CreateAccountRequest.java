package com.cmc.invitaservice.models.external.request;

import com.cmc.invitaservice.repositories.entities.VerifyUserToken;
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

    public void setAccount(VerifyUserToken verifyUserToken){
        this.username = verifyUserToken.getUsername();
        this.password = verifyUserToken.getPassword();
        this.firstName = verifyUserToken.getFirstName();
        this.lastName = verifyUserToken.getLastName();
        this.email = verifyUserToken.getEmail();
        this.retypePassword = verifyUserToken.getPassword();
    }
}
