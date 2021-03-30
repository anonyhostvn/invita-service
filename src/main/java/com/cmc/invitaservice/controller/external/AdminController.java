package com.cmc.invitaservice.controller.external;

import com.cmc.invitaservice.models.external.request.UpdateAccountRequest;
import com.cmc.invitaservice.response.GeneralResponse;
import com.cmc.invitaservice.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(value = "*")
@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    @GetMapping(path = "/user/all")
    public ResponseEntity<GeneralResponse<Object>> getAllAccount(){
        return adminService.getAllAccount();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<GeneralResponse<Object>> getTemplateById(@PathVariable(name="userId") Long userId){
        return adminService.getUserById(userId);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<GeneralResponse<Object>> deleteUserById(@PathVariable(name="userId") Long userId){
        return adminService.deleteUserById(userId);
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<GeneralResponse<Object>> changeUserById(@PathVariable(name="userId") Long userId,
                                                                  @RequestBody UpdateAccountRequest updateAccountRequest){
        return adminService.changeUserById(userId, updateAccountRequest);
    }
}
