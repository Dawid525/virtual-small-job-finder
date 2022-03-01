package com.pentagon.cafe.virtualSmallJobFinder.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message){
        super(message);
    }
}
