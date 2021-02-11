package com.cmc.invitaservice.controller.external;

import com.cmc.invitaservice.models.external.request.CreateAccountRequest;
import com.cmc.invitaservice.response.ResponseFactory;
import com.cmc.invitaservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{userId}")
    public ResponseEntity getInfoById(@PathVariable(name="userId") Long userId){
        return ResponseFactory.success(userService.getApplicationUserbyId(userId));
    }

    @GetMapping("/all")
    public ResponseEntity getInfoAll(){
        return ResponseFactory.success(userService.getAllApplicationUser());
    }
}
