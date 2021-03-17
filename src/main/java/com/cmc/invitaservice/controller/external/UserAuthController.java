package com.cmc.invitaservice.controller.external;

import com.cmc.invitaservice.models.external.request.ChangePasswordRequest;
import com.cmc.invitaservice.response.ResponseFactory;
import com.cmc.invitaservice.response.ResponseStatusEnum;
import com.cmc.invitaservice.service.UserService;
import com.cmc.invitaservice.service.ValidRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAuthController {

    private UserService userService;
    private ValidRequestService validRequest;

    public UserAuthController(UserService userService, ValidRequestService validRequest){
        this.userService = userService;
        this.validRequest = validRequest;
    }

    @PostMapping("edit/changepassword")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest){
        if (!validRequest.formatUsernameAndPassword(changePasswordRequest.getNewPassword()))
            return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.PASSWORD_ERROR);
        if (!validRequest.checkRetypePassword(changePasswordRequest.getRetypeNewPassword(),changePasswordRequest.getNewPassword()))
            return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.RETYPE_ERROR);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!userService.changePassword(userDetails.getUsername(), changePasswordRequest))
            return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.RETYPE_OLD_PASSWORD_ERROR);
        return ResponseFactory.success("Password has changed !");
    }
}
