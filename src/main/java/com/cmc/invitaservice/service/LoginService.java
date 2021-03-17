package com.cmc.invitaservice.service;

import com.cmc.invitaservice.models.external.request.LoginRequest;
import com.cmc.invitaservice.models.external.response.LoginResponse;

public interface LoginService {
    LoginResponse userDetailsImplement(LoginRequest loginRequest);
}
