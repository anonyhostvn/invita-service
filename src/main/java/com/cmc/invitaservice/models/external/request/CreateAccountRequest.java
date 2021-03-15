package com.cmc.invitaservice.models.external.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateAccountRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String retypePassword;
    @NotBlank
    private  String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private  String email;

    private boolean checkCharacter(char character){
        return (character >= 'a' && character <= 'z') || (character >= 'A' && character <= 'Z');
    }

    private boolean checkNum(char num){
        return num >= '0' && num <= '9';
    }

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

    public boolean formatName(CreateAccountRequest createAccountRequest){
        return checkName(createAccountRequest.getFirstName()) && checkName(createAccountRequest.getLastName());
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

    public boolean checkRetypePassword(String retypePassword, String password){
        return retypePassword.equals(password);
    }
}
