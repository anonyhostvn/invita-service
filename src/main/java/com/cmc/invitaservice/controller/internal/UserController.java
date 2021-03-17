package com.cmc.invitaservice.controller.internal;

import com.cmc.invitaservice.models.external.request.CreateAccountRequest;
import com.cmc.invitaservice.models.external.request.LoginRequest;
import com.cmc.invitaservice.models.external.response.LoginResponse;
import com.cmc.invitaservice.response.ResponseFactory;
import com.cmc.invitaservice.response.ResponseStatusEnum;
import com.cmc.invitaservice.service.LoginService;
import com.cmc.invitaservice.service.UserService;
import com.cmc.invitaservice.service.ValidRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private ValidRequestService validRequest;
    private LoginService loginService;

    public UserController(UserService userService, ValidRequestService validRequest, LoginService loginService){
        this.userService = userService;
        this.validRequest = validRequest;
        this.loginService = loginService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody CreateAccountRequest createAccountRequest){
        if (!validRequest.formatUsernameAndPassword(createAccountRequest.getUsername()))
            return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.USERNAME_ERROR);
        if (userService.findUsername(createAccountRequest.getUsername()))
            return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.USER_EXIST);
        if (!validRequest.formatUsernameAndPassword(createAccountRequest.getPassword()))
            return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.PASSWORD_ERROR);
        if (!validRequest.checkRetypePassword(createAccountRequest.getRetypePassword(),createAccountRequest.getPassword()))
            return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.RETYPE_ERROR);
        if (!validRequest.formatName(createAccountRequest))
            return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.NAME_ERROR);
        if (!validRequest.formatEmail(createAccountRequest.getEmail()))
            return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.EMAIL_ERROR);
        if (userService.findEmail(createAccountRequest.getEmail()))
            return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.EMAIL_EXIST);
        return ResponseFactory.success(userService.addAccount(createAccountRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid  @RequestBody LoginRequest loginRequest){
        if (!userService.checkAccount(loginRequest))
            return ResponseFactory.error(HttpStatus.valueOf(403), ResponseStatusEnum.WRONG_USERNAME_OR_PASSWORD);
        LoginResponse loginResponse = loginService.userDetailsImplement(loginRequest);
        return ResponseFactory.success(loginResponse);
    }
}
