package com.cmc.invitaservice.service.implement;

import com.cmc.invitaservice.models.external.response.GetAllApplicationUserResponse;
import com.cmc.invitaservice.repositories.ApplicationUserRepository;
import com.cmc.invitaservice.repositories.entities.ApplicationUser;
import com.cmc.invitaservice.repositories.entities.Role;
import com.cmc.invitaservice.response.GeneralResponse;
import com.cmc.invitaservice.response.ResponseFactory;
import com.cmc.invitaservice.response.ResponseStatusEnum;
import com.cmc.invitaservice.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImplement implements AdminService {
    private final ApplicationUserRepository applicationUserRepository;

    public AdminServiceImplement(ApplicationUserRepository applicationUserRepository){
        this.applicationUserRepository = applicationUserRepository;
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
        if (applicationUser.getEmail() == null)
            return ResponseFactory.error(HttpStatus.valueOf(400), ResponseStatusEnum.DELETE_ADMIN);
        applicationUserRepository.deleteById(userId);
        return ResponseFactory.success("delete succesfully");
    }
}
