package com.cmc.invitaservice.response;

import lombok.Getter;

@Getter
public enum ResponseStatusEnum {

    SUCCESS("SUCCESS", "Request successfully"),
    UNKNOWN_ERROR("E-000", "Can not specify error"),
    NOT_ENOUGH_PARAM("E-001", "Not enough param in request"),
    PASSWORD_ERROR("E-002", "Password must be between 8 and 50 character including numbers and letters"),
    USERNAME_ERROR("E-003", "Username must be between 8 and 50 character including numbers and letters"),
    WRONG_USERNAME_OR_PASSWORD("E-004", "Username or password is wrong"),
    NAME_ERROR("E-005", "Name must be characters"),
    USER_EXIST("E-006", "User already exists"),
    EMAIL_EXIST("E-007", "Email was registered"),
    EMAIL_ERROR("E-008", "Email is wrong format");

    private final String code;
    private final String message;

    ResponseStatusEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
