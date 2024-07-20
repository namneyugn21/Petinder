package com.petinder.pet_service.exception;

import java.util.UUID;

public class PetNotFound extends RuntimeException {
    public PetNotFound(UUID id) {
        super("Pet with id " + id + " not found");
    }
}
