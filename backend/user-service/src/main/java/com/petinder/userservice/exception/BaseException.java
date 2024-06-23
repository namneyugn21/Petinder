package com.petinder.userservice.exception;

public abstract class BaseException extends RuntimeException {
    public BaseException(String message) {
        super(message);
    }
}
