package com.cmc.invitaservice.service.implement;

import com.cmc.invitaservice.models.external.request.ChangePasswordRequest;
import com.cmc.invitaservice.models.external.request.CreateAccountRequest;
import com.cmc.invitaservice.models.external.request.LoginRequest;
import com.cmc.invitaservice.repositories.ApplicationUserRepository;
import com.cmc.invitaservice.repositories.RoleRepository;
import com.cmc.invitaservice.repositories.entities.ApplicationUser;
import com.cmc.invitaservice.repositories.entities.ERole;
import com.cmc.invitaservice.repositories.entities.Role;
import com.cmc.invitaservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class UserServiceImplement implements UserService{
    private ApplicationUserRepository applicationUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImplement(ApplicationUserRepository applicationUserRepository,
                                BCryptPasswordEncoder bCryptPasswordEncoder,
                                RoleRepository roleRepository){
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public ApplicationUser addAccount(CreateAccountRequest createAccountRequest){
        Set<Role> roles = new HashSet<>();
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setCreateAccountRequest(createAccountRequest);
        applicationUser.setPassword(bCryptPasswordEncoder.encode(applicationUser.getPassword()));
        roles.add(roleRepository.findByName(ERole.ROLE_USER));
        applicationUser.setRoles(roles);
        applicationUserRepository.save(applicationUser);
        return applicationUser;
    }

    @Override
    public boolean checkAccount(LoginRequest loginRequest){
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(loginRequest.getUsername());
        return applicationUser != null && new BCryptPasswordEncoder().matches(loginRequest.getPassword(), applicationUser.getPassword());
    }

    @Override
    public boolean findUsername(String username){
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
        return applicationUser != null;
    }

    @Override
    public boolean findEmail(String email){
        ApplicationUser applicationUser = applicationUserRepository.findByEmail(email);
        return applicationUser != null;
    }

    @Override
    public boolean changePassword(String username, ChangePasswordRequest changePasswordRequest){
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
        if (new BCryptPasswordEncoder().matches(changePasswordRequest.getOldPassword(), applicationUser.getPassword())) {
            applicationUser.setPassword(bCryptPasswordEncoder.encode(changePasswordRequest.getNewPassword()));
            applicationUserRepository.save(applicationUser);
            return true;
        }
        return false;
    }
}
