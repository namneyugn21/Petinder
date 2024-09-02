package com.petinder.userservice.exception.white;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public final class UserNotFound extends WhiteException {
    private static final String CODE = "UNF";
    private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

    public UserNotFound(UUID userId) {
        super(CODE, "No user with id " + userId, STATUS);
    }
}
