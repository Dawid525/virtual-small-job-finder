package com.pentagon.cafe.virtualSmallJobFinder.exceptions;


    public class UserWithThisUsernameExistsException extends RuntimeException {
        public UserWithThisUsernameExistsException(String message) {
            super(message);
        }
    }