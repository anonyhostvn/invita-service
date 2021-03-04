package com.cmc.invitaservice.service.implement;

import com.cmc.invitaservice.models.external.request.CreateAccountRequest;
import com.cmc.invitaservice.models.external.request.LoginRequest;
import com.cmc.invitaservice.models.external.response.GetAllApplicationUserResponse;
import com.cmc.invitaservice.repositories.ApplicationUserRepository;
import com.cmc.invitaservice.repositories.entities.ApplicationUser;
import com.cmc.invitaservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImplement implements UserService{
    private ApplicationUserRepository applicationUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImplement(ApplicationUserRepository applicationUserRepository,
                                BCryptPasswordEncoder bCryptPasswordEncoder){
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public ApplicationUser addAccount(CreateAccountRequest createAccountRequest){
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setCreateAccountRequest(createAccountRequest);
        applicationUser.setPassword(bCryptPasswordEncoder.encode(applicationUser.getPassword()));
        applicationUserRepository.save(applicationUser);
        return applicationUser;
    }

    @Override
    public ApplicationUser getAccount(LoginRequest loginRequest){
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(loginRequest.getUsername());
        if (bCryptPasswordEncoder.matches(loginRequest.getPassword(), applicationUser.getPassword()))
            return applicationUser;
        return null;
    }
}
