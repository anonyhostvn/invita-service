package com.cmc.invitaservice.service.implement;

import com.cmc.invitaservice.models.external.request.UpdateAccountRequest;
import com.cmc.invitaservice.models.external.response.GetAllApplicationUserResponse;
import com.cmc.invitaservice.repositories.ApplicationUserRepository;
import com.cmc.invitaservice.repositories.entities.ApplicationUser;
import com.cmc.invitaservice.response.GeneralResponse;
import com.cmc.invitaservice.response.ResponseFactory;
import com.cmc.invitaservice.response.ResponseStatusEnum;
import com.cmc.invitaservice.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImplement implements AdminService {
    private final ApplicationUserRepository applicationUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ValidationService validationService;

    public AdminServiceImplement(ApplicationUserRepository applicationUserRepository,
                                 BCryptPasswordEncoder bCryptPasswordEncoder,
                                 ValidationService validationService){
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.validationService = validationService;
    }

    @Override
    public GetAllApplicationUserResponse getAllAccount(){
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();

        GetAllApplicationUserResponse getAllApplicationUserResponse = new GetAllApplicationUserResponse();
        getAllApplicationUserResponse.setListApplicationUser(applicationUserList);
        return getAllApplicationUserResponse;
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> getUserById(Long userId){
        ApplicationUser applicationUser = applicationUserRepository.findApplicationUserById(userId);
        if (applicationUser == null)
            return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.NOT_EXIST);
        return ResponseFactory.success(applicationUser, ApplicationUser.class);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> deleteUserById(Long userId){
        ApplicationUser applicationUser = applicationUserRepository.findApplicationUserById(userId);
        if (applicationUser == null)
            return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.NOT_EXIST);
        if (userId == 1)
            return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.NOT_UPDATE_ADMIN);
        applicationUserRepository.deleteById(userId);
        return ResponseFactory.success("delete succesfully");
    }

    private ResponseEntity<GeneralResponse<Object>> validateSignUp(UpdateAccountRequest updateAccountRequest){
        return validationService.validRequest(
                updateAccountRequest.getUsername(),
                updateAccountRequest.getPassword(),
                updateAccountRequest.getFirstName(),
                updateAccountRequest.getLastName(),
                updateAccountRequest.getEmail());
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> changeUserById(Long userId, UpdateAccountRequest updateAccountRequest){
        if (userId == 1)
            return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.NOT_UPDATE_ADMIN);

        ResponseEntity<GeneralResponse<Object>> validateResult = validateSignUp(updateAccountRequest);
        if (validateResult != null) return validateResult;

        ApplicationUser applicationUser = applicationUserRepository.findApplicationUserById(userId);
        if (applicationUser == null)
            return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.NOT_EXIST);
        applicationUser.setUpdateAccountRequest(updateAccountRequest);
        applicationUser.setPassword(bCryptPasswordEncoder.encode(updateAccountRequest.getPassword()));
        applicationUserRepository.save(applicationUser);

        return ResponseFactory.success(applicationUserRepository.findApplicationUserById(userId));
    }
}
