package com.cmc.invitaservice.controller.internal;

import com.cmc.invitaservice.models.external.request.CreateAccountRequest;
import com.cmc.invitaservice.models.external.request.LoginRequest;
import com.cmc.invitaservice.response.GeneralResponse;
import com.cmc.invitaservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(value = "*")
@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<GeneralResponse<Object>> signUp(@RequestBody CreateAccountRequest createAccountRequest){
        return userService.signupAccount(createAccountRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<GeneralResponse<Object>> login(@Valid  @RequestBody LoginRequest loginRequest){
        return userService.loginAccount(loginRequest);
    }
}
