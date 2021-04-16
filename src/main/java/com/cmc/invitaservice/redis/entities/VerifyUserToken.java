package com.cmc.invitaservice.redis.entities;

import com.cmc.invitaservice.models.external.request.CreateAccountRequest;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class VerifyUserToken implements Serializable {
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