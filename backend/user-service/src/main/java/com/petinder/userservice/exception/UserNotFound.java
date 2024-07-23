package com.petinder.userservice.exception;

import java.util.UUID;

public class UserNotFound extends BaseException {
    public UserNotFound(UUID userId) {
        super("No user with id " + userId);
    }
}
