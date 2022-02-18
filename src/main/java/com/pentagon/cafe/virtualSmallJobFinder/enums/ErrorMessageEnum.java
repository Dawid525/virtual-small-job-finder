package com.pentagon.cafe.virtualSmallJobFinder.enums;

public enum ErrorMessageEnum {

    NOT_FOUND_USERNAME("Not found user with username: "),
    NOT_FOUND_USER("Not found user with id:"),
    USERNAME_IS_NOT_AVAILABLE(" username is not available"),
    EMAIL_IS_NOT_AVAILABLE(" email is not available");

    private final String message;

    ErrorMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
