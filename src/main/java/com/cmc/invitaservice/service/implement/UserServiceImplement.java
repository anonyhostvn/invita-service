package com.cmc.invitaservice.service.implement;

import com.cmc.invitaservice.models.external.request.ChangePasswordRequest;
import com.cmc.invitaservice.models.external.request.CreateAccountRequest;
import com.cmc.invitaservice.models.external.request.LoginRequest;
import com.cmc.invitaservice.models.external.response.LoginResponse;
import com.cmc.invitaservice.repositories.ApplicationUserRepository;
import com.cmc.invitaservice.repositories.RoleRepository;
import com.cmc.invitaservice.repositories.entities.ApplicationUser;
import com.cmc.invitaservice.repositories.entities.ERole;
import com.cmc.invitaservice.repositories.entities.Role;
import com.cmc.invitaservice.response.GeneralResponse;
import com.cmc.invitaservice.response.ResponseFactory;
import com.cmc.invitaservice.response.ResponseStatusEnum;
import com.cmc.invitaservice.security.filter.JWT.JwtUtils;
import com.cmc.invitaservice.security.filter.service.UserDetailsImplement;
import com.cmc.invitaservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImplement implements UserService{
    private final ApplicationUserRepository applicationUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;
    private final ValidationService validationService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    public UserServiceImplement(ApplicationUserRepository applicationUserRepository,
                                BCryptPasswordEncoder bCryptPasswordEncoder,
                                RoleRepository roleRepository,
                                ValidationService validationService){
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
        this.validationService= validationService;
    }

    public ResponseEntity<GeneralResponse<Object>> addAccount(CreateAccountRequest createAccountRequest){
        Set<Role> roles = new HashSet<>();
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setCreateAccountRequest(createAccountRequest);
        applicationUser.setPassword(bCryptPasswordEncoder.encode(applicationUser.getPassword()));
        roles.add(roleRepository.findByName(ERole.ROLE_USER));
        applicationUser.setRoles(roles);
        applicationUserRepository.save(applicationUser);
        return ResponseFactory.success(applicationUser, ApplicationUser.class);
    }

    private ResponseEntity<GeneralResponse<Object>> checkAccount(LoginRequest loginRequest) {
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(loginRequest.getUsername());
        if (applicationUser != null && new BCryptPasswordEncoder().matches(loginRequest.getPassword(), applicationUser.getPassword())) return null;
        return ResponseFactory.error(HttpStatus.valueOf(403), ResponseStatusEnum.WRONG_USERNAME_OR_PASSWORD);

    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> changePassword(ChangePasswordRequest changePasswordRequest){
        ResponseEntity<GeneralResponse<Object>> validateResult = validationService.validateChangePassword(changePasswordRequest.getNewPassword(), changePasswordRequest.getRetypeNewPassword());
        if (validateResult != null) return validateResult;

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
        if (new BCryptPasswordEncoder().matches(changePasswordRequest.getOldPassword(), applicationUser.getPassword())) {
            applicationUser.setPassword(bCryptPasswordEncoder.encode(changePasswordRequest.getNewPassword()));
            applicationUserRepository.save(applicationUser);
            return ResponseFactory.success("Password has changed !");
        }
        return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.RETYPE_OLD_PASSWORD_ERROR);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> loginAccount(LoginRequest loginRequest){
        ResponseEntity<GeneralResponse<Object>> loginResult = checkAccount(loginRequest);
        if (loginResult != null) return loginResult;

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJWT(authentication);

        UserDetailsImplement userDetailsImplement = (UserDetailsImplement) authentication.getPrincipal();
        List<String> roles = userDetailsImplement.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        LoginResponse loginResponse = new LoginResponse(jwt, userDetailsImplement.getId(), userDetailsImplement.getUsername(), userDetailsImplement.getEmail(), roles);
        return ResponseFactory.success(loginResponse, LoginResponse.class);
    }

    private ResponseEntity<GeneralResponse<Object>> validateSignUp(CreateAccountRequest createAccountRequest){
        if (!validationService.checkRetypePassword(createAccountRequest.getRetypePassword(),createAccountRequest.getPassword()))
            return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.RETYPE_ERROR);
        return validationService.validRequest(
                createAccountRequest.getUsername(),
                createAccountRequest.getPassword(),
                createAccountRequest.getFirstName(),
                createAccountRequest.getLastName(),
                createAccountRequest.getEmail());
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> signupAccount(CreateAccountRequest createAccountRequest){
        ResponseEntity<GeneralResponse<Object>> validateResult = validateSignUp(createAccountRequest);
        if (validateResult != null) return validateResult;
        return addAccount(createAccountRequest);
    }
}
