package com.cmc.invitaservice.service.implement;

import com.cmc.invitaservice.mailsender.EmailService;
import com.cmc.invitaservice.models.external.request.*;
import com.cmc.invitaservice.models.external.response.LoginResponse;
import com.cmc.invitaservice.redis.entities.VerifyUserToken;
import com.cmc.invitaservice.redis.service.IRedisCaching;
import com.cmc.invitaservice.repositories.ApplicationUserRepository;
import com.cmc.invitaservice.repositories.RefreshTokenRepository;
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
import com.cmc.invitaservice.service.config.ValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.cmc.invitaservice.security.SecurityConstants.MANAGEMENT_MAIL;
import static com.cmc.invitaservice.security.SecurityConstants.VERIFY_KEY;

@Service
@Slf4j
public class UserServiceImplement implements UserService{

    @Value("${token.resetUrl}")
    private String resetUrl;

    @Value("${token.ttl}")
    private Long ttl;

    @Value("${token.verifyUrl}")
    private String verifyUrl;

    private final ApplicationUserRepository applicationUserRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final RoleRepository roleRepository;

    private final ValidationService validationService;

    private final IRedisCaching iRedisCaching;

    private final AuthenticationManager authenticationManager;

    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtUtils jwtUtils;

    private final EmailService emailService;

    public UserServiceImplement(ApplicationUserRepository applicationUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository, ValidationService validationService, IRedisCaching iRedisCaching, AuthenticationManager authenticationManager, JwtUtils jwtUtils, EmailService emailService,    RefreshTokenRepository refreshTokenRepository) {
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
        this.validationService = validationService;
        this.iRedisCaching = iRedisCaching;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.emailService = emailService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    private ResponseEntity<GeneralResponse<Object>> addAccount(CreateAccountRequest createAccountRequest){
        Set<Role> roles = new HashSet<>();
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setCreateAccountRequest(createAccountRequest);
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

    private void sendMail(String mail, String url, String token){
        CompletableFuture.runAsync(() ->
                emailService.sendEmail(MANAGEMENT_MAIL,
                        mail,
                        "Password Reset Request",
                        "To reset your password, click the link below:\n" + url + token)
        );
        log.info("Mail has been sent!");
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
        if (refreshTokenRepository.findByUsername(loginRequest.getUsername()) != null)
            return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.ACCOUNT_LOGGED_IN);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJWT(authentication);
        String jwt1 = jwtUtils.generateRefreshToken(authentication);
        UserDetailsImplement userDetailsImplement = (UserDetailsImplement) authentication.getPrincipal();
        List<String> roles = userDetailsImplement.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        LoginResponse loginResponse = new LoginResponse(jwt1, jwt, userDetailsImplement.getId(), userDetailsImplement.getUsername(), userDetailsImplement.getEmail(), roles);
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
    public ResponseEntity<GeneralResponse<Object>> signupAccount(CreateAccountRequest createAccountRequest, HttpServletRequest request){
        ResponseEntity<GeneralResponse<Object>> validateResult = validateSignUp(createAccountRequest);
        if (validateResult != null) return validateResult;
        String token = UUID.randomUUID().toString();
        createAccountRequest.setPassword(bCryptPasswordEncoder.encode(createAccountRequest.getPassword()));
        VerifyUserToken verifyUserToken = new VerifyUserToken(createAccountRequest);
        iRedisCaching.addToHash(token, VERIFY_KEY, verifyUserToken, ttl);
        sendMail(createAccountRequest.getEmail(), verifyUrl, token);
        return ResponseFactory.success("Waiting for verification");
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> verifySignUp(Map<String, String> requestParam){
        String token = requestParam.get("token");
        VerifyUserToken verifyUserToken = (VerifyUserToken) iRedisCaching.getFromHash(token, VERIFY_KEY);
        if (verifyUserToken == null)
            return ResponseFactory.error(HttpStatus.valueOf(403), ResponseStatusEnum.UNKNOWN_ERROR);
        CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setAccount(verifyUserToken);
        ResponseEntity<GeneralResponse<Object>> validateResult = validationService.validExist(createAccountRequest.getUsername(), createAccountRequest.getEmail());
        if (validateResult != null) return validateResult;
        iRedisCaching.removeFromHash(token, VERIFY_KEY);
        return addAccount(createAccountRequest);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> forgotPassword(ForgotPasswordRequest forgotPasswordRequest, HttpServletRequest request){
        ApplicationUser applicationUser = applicationUserRepository.findByEmail(forgotPasswordRequest.getEmail());
        if (applicationUser == null)
            return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.NOT_EXIST);
        String token = UUID.randomUUID().toString();
        iRedisCaching.addOpsValue(token, applicationUser.getId(), ttl);
        sendMail(applicationUser.getEmail(), resetUrl, token);
        return ResponseFactory.success("A password reset link has been sent to " + applicationUser.getUsername());
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> resetPassword(ResetPasswordRequest resetPasswordRequest, Map<String, String> requestParam ) {
        String token = requestParam.get("token");
        String userId = (String) iRedisCaching.getFromOpsValue(token);
        if (userId == null)
            return ResponseFactory.error(HttpStatus.valueOf(403), ResponseStatusEnum.UNKNOWN_ERROR);
        ResponseEntity<GeneralResponse<Object>> validateResult = validationService.validateChangePassword(resetPasswordRequest.getPassword(), resetPasswordRequest.getRetypePassword());
        if (validateResult != null) return validateResult;
        ApplicationUser applicationUser = applicationUserRepository.findApplicationUserById(Long.valueOf(userId));
        applicationUser.setPassword(bCryptPasswordEncoder.encode(resetPasswordRequest.getPassword()));
        applicationUserRepository.save(applicationUser);
        iRedisCaching.removeFromOpsValue(token);
        return ResponseFactory.success("Password has changed !");
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> logoutAccount(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        refreshTokenRepository.deleteByUsername(userDetails.getUsername());
        return ResponseFactory.success("Logout successfully!");
    }
}
