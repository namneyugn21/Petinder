package com.petinder.pet_service.exception;

import java.util.UUID;

public class UserNotFound extends RuntimeException {
    public UserNotFound(final UUID userId) {
        super("No user with ID: " + userId);
    }
}
