package com.petinder.pet_service.exception;

import java.util.UUID;

public class ShelterNotFound extends RuntimeException {
    public ShelterNotFound(UUID id) {
        super("No shelter with ID: " + id);
    }
}
