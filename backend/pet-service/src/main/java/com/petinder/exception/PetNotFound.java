package com.petinder.exception;

public class PetNotFound extends RuntimeException {
    public PetNotFound(String id) {
        super("Pet with id " + id + " not found");
    }
}
