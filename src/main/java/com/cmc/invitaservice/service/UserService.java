package com.cmc.invitaservice.service;

import com.cmc.invitaservice.models.external.request.ChangePasswordRequest;
import com.cmc.invitaservice.models.external.request.CreateAccountRequest;
import com.cmc.invitaservice.models.external.request.LoginRequest;
import com.cmc.invitaservice.models.external.request.ResetPasswordRequest;
import com.cmc.invitaservice.response.GeneralResponse;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    ResponseEntity<GeneralResponse<Object>> loginAccount(LoginRequest loginRequest);
    ResponseEntity<GeneralResponse<Object>> changePassword(ChangePasswordRequest changePasswordRequest);
    ResponseEntity<GeneralResponse<Object>> signupAccount(CreateAccountRequest createAccountRequest);
    ResponseEntity<GeneralResponse<Object>> resetPassword(ResetPasswordRequest resetPasswordRequest, HttpServletRequest httpServletRequest);
}
