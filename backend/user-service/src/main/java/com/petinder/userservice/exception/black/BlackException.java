package com.petinder.userservice.exception.black;

import com.petinder.userservice.exception.BaseException;
import org.springframework.http.HttpStatus;

/**
 * Exception that is hidden to the user
 */
public abstract class BlackException extends BaseException {
    private static final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    public BlackException(String code, String message) {
        this(code, message, status, null);
    }

    public BlackException(String code, String message, HttpStatus status) {
        this(code, message, status, null);
    }

    public BlackException(String code, String message, HttpStatus status, Throwable e) {
        super(code, message, status, e);
    }
}
