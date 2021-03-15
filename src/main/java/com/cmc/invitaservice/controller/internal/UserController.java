package com.cmc.invitaservice.controller.internal;

import com.cmc.invitaservice.models.external.request.CreateAccountRequest;
import com.cmc.invitaservice.models.external.request.LoginRequest;
import com.cmc.invitaservice.models.external.request.ValidRequest;
import com.cmc.invitaservice.models.external.response.JwtResponse;
import com.cmc.invitaservice.response.ResponseFactory;
import com.cmc.invitaservice.response.ResponseStatusEnum;
import com.cmc.invitaservice.security.filter.JWT.JwtUtils;
import com.cmc.invitaservice.security.filter.service.UserDetailsImplement;
import com.cmc.invitaservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private ValidRequest validRequest;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    public UserController(UserService userService, ValidRequest validRequest){
        this.userService = userService;
        this.validRequest = validRequest;
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
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJWT(authentication);

        UserDetailsImplement userDetailsImplement = (UserDetailsImplement) authentication.getPrincipal();
        List<String> roles = userDetailsImplement.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return ResponseFactory.success(new JwtResponse( jwt, userDetailsImplement.getId(), userDetailsImplement.getUsername(), userDetailsImplement.getEmail(), roles));
    }
}
