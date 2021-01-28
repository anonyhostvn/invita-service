package com.cmc.invitaservice.exceptions;

import com.cmc.invitaservice.response.ResponseStatusEnum;

public class CustomBusinessLogicException  extends ApplicationException{

    private static final long serialVersionUID = -1605187590106478545L;

    public CustomBusinessLogicException(ResponseStatusEnum responseStatusEnum) {
        super(responseStatusEnum);
    }
}
