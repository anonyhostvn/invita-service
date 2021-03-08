package com.cmc.invitaservice.service;

import com.cmc.invitaservice.models.external.request.CreateAccountRequest;
import com.cmc.invitaservice.models.external.request.LoginRequest;
import com.cmc.invitaservice.repositories.entities.ApplicationUser;

public interface UserService {
    ApplicationUser addAccount(CreateAccountRequest createAccountRequest);
    boolean findUsername(String username);
    boolean formatUsernameAndPassword(String username);
    boolean formatName(CreateAccountRequest createAccountRequest);
    boolean formatEmail(String email);
    boolean findEmail(String email);
}
