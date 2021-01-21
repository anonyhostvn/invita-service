package com.cmc.invitaservice.exceptions;


import com.cmc.invitaservice.response.ResponseStatusEnum;
import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
    private static final long serialVersionUID = -578756703743877447L;

    private final ResponseStatusEnum responseStatusEnum;

    // The message is used when you have a detail message which is difference code
    // The message is only used to log, is wont show to end user

    public ApplicationException(ResponseStatusEnum responseStatusEnum) {
        this.responseStatusEnum = responseStatusEnum;
    }
}

