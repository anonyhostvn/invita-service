package com.cmc.invitaservice.controller.internal;

import com.cmc.invitaservice.models.external.request.CreateAccountRequest;
import com.cmc.invitaservice.response.ResponseFactory;
import com.cmc.invitaservice.response.ResponseStatusEnum;
import com.cmc.invitaservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity signUp(@RequestBody CreateAccountRequest createAccountRequest){
        if (!createAccountRequest.formatUsernameAndPassword(createAccountRequest.getUsername()))
            return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.USERNAME_ERROR);
        if (userService.findUsername(createAccountRequest.getUsername()))
            return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.USER_EXIST);
        if (!createAccountRequest.formatUsernameAndPassword(createAccountRequest.getPassword()))
            return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.PASSWORD_ERROR);
        if (!createAccountRequest.formatName(createAccountRequest))
            return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.NAME_ERROR);
        if (!createAccountRequest.formatEmail(createAccountRequest.getEmail()))
            return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.EMAIL_ERROR);
        if (userService.findEmail(createAccountRequest.getEmail()))
            return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.EMAIL_EXIST);
        return ResponseFactory.success(userService.addAccount(createAccountRequest));
    }

}
