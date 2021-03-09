package com.cmc.invitaservice.service;

import com.cmc.invitaservice.models.external.request.CreateAccountRequest;
import com.cmc.invitaservice.repositories.entities.ApplicationUser;

public interface UserService {
    ApplicationUser addAccount(CreateAccountRequest createAccountRequest);
    boolean findUsername(String username);
    boolean findEmail(String email);
}
