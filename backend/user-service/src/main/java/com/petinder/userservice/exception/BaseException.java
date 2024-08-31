package com.petinder.userservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class BaseException extends RuntimeException {
    public static final String DEFAULT_CODE = "UNKNOWN";
    public static final HttpStatus DEFAULT_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;
    public static final String DEFAULT_MSG = "Something went wrong, please contact us if the problem persist";

    private static final String CODE_PREFIX = "USER";
    private final String code;
    private final HttpStatus status;
    private final List<String> messages;

    public BaseException() {
        this(DEFAULT_MSG);
    }

    public BaseException(String message) {
        this(DEFAULT_CODE, message);
    }

    public BaseException(List<String> messages) {
        this(DEFAULT_CODE, messages);
    }

    public BaseException(String code, String message) {
        this(code, message, DEFAULT_STATUS);
    }

    public BaseException(String code, List<String> messages) {
        this(code, messages, DEFAULT_STATUS);
    }

    public BaseException(String code, String message, HttpStatus status) {
        this(code, message, status, null);
    }

    public BaseException(String code, List<String> messages, HttpStatus status) {
        this(code, messages, status, null);
    }

    public BaseException(String code, String message, HttpStatus status, Throwable e) {
        super(message, e);
        this.code = createCode(code);
        this.status = status;
        this.messages = new ArrayList<>() {
            {
                add(message);
            }
        };
    }

    public BaseException(String code, List<String> messages, HttpStatus status, Throwable e) {
        super(messages.get(0), e);  // add only the first message (most important) for stack trace readability
        this.code = createCode(code);
        this.status = status;
        this.messages = new ArrayList<>(messages);
    }

    public static String createCode() {
        return createCode(DEFAULT_CODE);
    }

    public static String createCode(String code) {
        return CODE_PREFIX + "_" + code;
    }

    @Override
    public String toString() {
        final String superString = super.toString();
        return "BaseException{" +
                "code='" + code + '\'' +
                ", status=" + status +
                ", messages=" + messages +
                '}' +
                "\nStacktrace: " + superString + '\n';
    }
}
