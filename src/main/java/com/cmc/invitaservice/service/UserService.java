package com.cmc.invitaservice.service;

import com.cmc.invitaservice.models.external.request.ChangePasswordRequest;
import com.cmc.invitaservice.models.external.request.CreateAccountRequest;
import com.cmc.invitaservice.models.external.request.LoginRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity loginAccount(LoginRequest loginRequest);
    ResponseEntity changePassword(ChangePasswordRequest changePasswordRequest);
    ResponseEntity signupAccount(CreateAccountRequest createAccountRequest);
}
