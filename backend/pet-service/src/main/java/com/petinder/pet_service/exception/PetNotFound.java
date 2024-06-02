package com.petinder.pet_service.exception;

public class PetNotFound extends RuntimeException {
    public PetNotFound(String id) {
        super("Pet with id " + id + " not found");
    }
}
