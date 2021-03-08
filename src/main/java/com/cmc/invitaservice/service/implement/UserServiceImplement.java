package com.cmc.invitaservice.service.implement;

import com.cmc.invitaservice.models.external.request.CreateAccountRequest;
import com.cmc.invitaservice.models.external.request.LoginRequest;
import com.cmc.invitaservice.repositories.ApplicationUserRepository;
import com.cmc.invitaservice.repositories.entities.ApplicationUser;
import com.cmc.invitaservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
    public boolean findUsername(String username){
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
        return applicationUser != null;
    }

    private boolean checkCharacter(char character){
        return (character >= 'a' && character <= 'z') || (character >= 'A' && character <= 'Z');
    }

    private boolean checkNum(char num){
        return num >= '0' && num <= '9';
    }

    @Override
    public boolean formatUsernameAndPassword(String username){
        int length = username.length();
        if (length < 8 || length > 50) return false;
        for (int i=0; i<length; i++) {
            char character = username.charAt(i);
            if (!checkCharacter(character) && !checkNum(character)) return false;
        }
        return true;
    }

    private boolean checkName(String name){
        int length = name.length();
        if (length > 50) return false;
        for (int i=0; i<length; i++){
            char character = name.charAt(i);
            if (!checkCharacter(character)) return false;
        }
        return true;
    }

    @Override
    public boolean formatName(CreateAccountRequest createAccountRequest){
        return checkName(createAccountRequest.getFirstName()) && checkName(createAccountRequest.getLastName());
    }

    @Override
    public boolean findEmail(String email){
        ApplicationUser applicationUser = applicationUserRepository.findByEmail(email);
        return applicationUser != null;
    }

    private boolean checkEmailPart(String emailPart){
        char character = emailPart.charAt(0);
        int pre = -1;
        if (!checkCharacter(character) && !checkNum(character)) return false;
        character = emailPart.charAt(emailPart.length()-1);
        if (!checkCharacter(character) && !checkNum(character)) return false;
        int length = emailPart.length();
        for (int i=0; i<length; i++){
            character = emailPart.charAt(i);
            if (!checkCharacter(character) && !checkNum(character) && character != '.') return false;
            if (character == '.') {
                if (i == pre + 1) return false;
                pre = i;
            }
        }
        return true;
    }

    @Override
    public boolean formatEmail(String email){
        if (email.length() > 100 || email.length() < 10) return false;
        int pos = email.indexOf('@');
        if (pos<1) return false;
        if (pos == email.length()-1) return false;
        if (!checkEmailPart(email.substring(0, pos))) return false;
        String emailPart = email.substring(pos + 1);
        if (emailPart.indexOf('.') == -1) return false;
        return checkEmailPart(emailPart);
    }

}
