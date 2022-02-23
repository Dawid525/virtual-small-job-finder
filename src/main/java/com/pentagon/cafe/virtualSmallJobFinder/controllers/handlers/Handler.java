package com.pentagon.cafe.virtualSmallJobFinder.controllers.handlers;

import com.pentagon.cafe.virtualSmallJobFinder.exceptions.TokenRefreshException;
import com.pentagon.cafe.virtualSmallJobFinder.exceptions.UserWithThisEmailExistsException;
import com.pentagon.cafe.virtualSmallJobFinder.exceptions.UserWithThisUsernameExistsException;
import com.pentagon.cafe.virtualSmallJobFinder.services.dtos.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class Handler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TokenRefreshException.class)
    public ErrorDto handleTokenRefreshException(TokenRefreshException ex) {
        return new ErrorDto(ex.getMessage(), "11");
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserWithThisEmailExistsException.class)
    public ErrorDto handleUserWithThisEmailExistsException(UserWithThisEmailExistsException ex) {
        return new ErrorDto(ex.getMessage(), "12");
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserWithThisUsernameExistsException.class)
    public ErrorDto handleUserWithThisUsernameExistsException(UserWithThisUsernameExistsException ex) {
        return new ErrorDto(ex.getMessage(), "13");
    }
}
