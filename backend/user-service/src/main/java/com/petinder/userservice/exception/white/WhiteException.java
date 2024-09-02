package com.petinder.userservice.exception.white;

import com.petinder.userservice.exception.BaseException;
import org.springframework.http.HttpStatus;

/**
 * Exception that will display the message to the user
 */
public abstract class WhiteException extends BaseException {
    public WhiteException(String code, String message, HttpStatus status) {
        this(code, message, status, null);
    }

    public WhiteException(String code, String message, HttpStatus status, Throwable e) {
        super(code, message, status, e);
    }
}
