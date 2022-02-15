package com.pentagon.cafe.virtualSmallJobFinder.exceptions;

public class UserWithThisEmailExistsException extends RuntimeException {
    public UserWithThisEmailExistsException(String message) {
        super(message);

    }
}
