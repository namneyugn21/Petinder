package com.petinder.userservice.exception;

public class UserNotFound extends BaseException {
    public UserNotFound() {
        super("User not found");
    }
}
