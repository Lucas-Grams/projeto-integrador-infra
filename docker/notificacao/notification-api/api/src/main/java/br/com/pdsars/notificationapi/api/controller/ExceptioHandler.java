package br.com.pdsars.notificationapi.api.controller;

import br.com.pdsars.notificationapi.api.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptioHandler {

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseDTO<Void> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseDTO.err(ex.getMessage());
    }
}
