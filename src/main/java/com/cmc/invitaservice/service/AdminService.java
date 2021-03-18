package com.cmc.invitaservice.service;

import com.cmc.invitaservice.models.external.response.GetAllApplicationUserResponse;
import org.springframework.http.ResponseEntity;

public interface AdminService {
    GetAllApplicationUserResponse getAllAccount();
    ResponseEntity getUserById(Long userId);
    ResponseEntity deleteUserById(Long userId);
}
