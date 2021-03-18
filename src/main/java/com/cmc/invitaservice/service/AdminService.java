package com.cmc.invitaservice.service;

import com.cmc.invitaservice.models.external.request.UpdateAccountRequest;
import com.cmc.invitaservice.models.external.response.GetAllApplicationUserResponse;
import com.cmc.invitaservice.response.GeneralResponse;
import org.springframework.http.ResponseEntity;

public interface AdminService {
    GetAllApplicationUserResponse getAllAccount();
    ResponseEntity<GeneralResponse<Object>> getUserById(Long userId);
    ResponseEntity<GeneralResponse<Object>> deleteUserById(Long userId);
    ResponseEntity<GeneralResponse<Object>> changeUserById(Long userId, UpdateAccountRequest updateAccountRequest);
}
