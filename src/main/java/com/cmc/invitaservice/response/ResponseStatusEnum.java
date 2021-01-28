package com.cmc.invitaservice.response;

import lombok.Getter;

@Getter
public enum ResponseStatusEnum {

    SUCCESS("SUCCESS", "Request successfully"),
    UNKNOWN_ERROR("E-000", "Can not specify error"),
    NOT_ENOUGH_PARAM("E-001", "Not enough param in request"),
    BUSINESS_ERROR("E-003", "Business processing error"),
    STUDENT_NOT_FOUND("E-004", "Not found student");

    private final String code;
    private final String message;

    ResponseStatusEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
