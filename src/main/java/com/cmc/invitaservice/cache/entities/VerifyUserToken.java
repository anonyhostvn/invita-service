package com.cmc.invitaservice.cache.entities;

import com.cmc.invitaservice.models.external.request.CreateAccountRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VerifyUserToken{
    private static final long serialVersionUID = -6741886447122081418L;

    private String username;

    private String password;

    private  String firstName;

    private String lastName;

    private  String email;

    public VerifyUserToken(CreateAccountRequest createAccountRequest){
        this.username = createAccountRequest.getUsername();
        this.password = createAccountRequest.getPassword();
        this.firstName = createAccountRequest.getFirstName();
        this.lastName = createAccountRequest.getLastName();
        this.email = createAccountRequest.getEmail();
    }
}