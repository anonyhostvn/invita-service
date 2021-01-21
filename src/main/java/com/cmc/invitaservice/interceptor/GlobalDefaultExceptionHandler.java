package com.cmc.invitaservice.interceptor;


import com.cmc.invitaservice.exceptions.CustomBusinessLogicException;
import com.cmc.invitaservice.response.GeneralResponse;
import com.cmc.invitaservice.response.ResponseFactory;
import com.cmc.invitaservice.response.ResponseStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<GeneralResponse<Object>> defaultExceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseFactory.error(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.UNKNOWN_ERROR);
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseBody
    public ResponseEntity<GeneralResponse<Object>> missingRequestParamHandler(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseFactory.error(HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatusEnum.NOT_ENOUGH_PARAM);
    }

    @ExceptionHandler(value = CustomBusinessLogicException.class)
    @ResponseBody
    public ResponseEntity<GeneralResponse<Object>> businessExceptionHandler(CustomBusinessLogicException e) {
        log.error(e.getMessage(), e);
        return ResponseFactory.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getResponseStatusEnum());
    }
}
