package com.cmc.invitaservice.service;

import com.cmc.invitaservice.models.external.request.CreateAccountRequest;

public interface ValidRequestService {
    boolean formatUsernameAndPassword(String username);
    boolean formatName(CreateAccountRequest createAccountRequest);
    boolean formatEmail(String email);
    boolean checkRetypePassword(String retypePassword, String password);
}
