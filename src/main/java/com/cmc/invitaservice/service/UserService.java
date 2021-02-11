package com.cmc.invitaservice.service;

import com.cmc.invitaservice.models.external.request.CreateAccountRequest;
import com.cmc.invitaservice.models.external.response.GetAllApplicationUserResponse;
import com.cmc.invitaservice.repositories.entities.ApplicationUser;

public interface UserService {
    ApplicationUser addAccount(CreateAccountRequest createAccountRequest);
    ApplicationUser getApplicationUserbyId(Long Id);
    GetAllApplicationUserResponse getAllApplicationUser();
}
