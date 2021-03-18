package com.cmc.invitaservice.controller.external;

import com.cmc.invitaservice.models.external.response.GetAllApplicationUserResponse;
import com.cmc.invitaservice.response.GeneralResponse;
import com.cmc.invitaservice.response.ResponseFactory;
import com.cmc.invitaservice.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(value = "*")
@Slf4j
@RestController
@RequestMapping(path = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private AdminService adminService;

    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    @GetMapping(path = "/user/all")
    public ResponseEntity<GeneralResponse<GetAllApplicationUserResponse>> getAllAccount(){
        return ResponseFactory.success(adminService.getAllAccount());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity getTemplateById(@PathVariable(name="userId") Long userId){
        return adminService.getUserById(userId);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity deleteUserById(@PathVariable(name="userId") Long userId){
        return adminService.deleteUserById(userId);
    }
}