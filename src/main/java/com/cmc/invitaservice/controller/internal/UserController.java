package com.cmc.invitaservice.controller.internal;

import com.cmc.invitaservice.models.external.request.CreateAccountRequest;
import com.cmc.invitaservice.models.external.request.LoginRequest;
import com.cmc.invitaservice.response.ResponseFactory;
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
        return ResponseFactory.success(userService.addAccount(createAccountRequest));
    }

   @PostMapping("/login")
    public ResponseEntity logIn(@RequestBody LoginRequest loginRequest){
       if (loginRequest != null)
           if (userService.getAccount(loginRequest) != null) ResponseFactory.success();
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid");
   }
}
