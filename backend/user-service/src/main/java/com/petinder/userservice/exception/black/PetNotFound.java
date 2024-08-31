package com.petinder.userservice.exception.black;


import org.springframework.http.HttpStatus;

import java.util.UUID;

public final class PetNotFound extends BlackException {
    private static final String CODE = "PNF";
    private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

    public PetNotFound(final UUID petId) {
        super(CODE, "No pet with id " + petId, STATUS);
    }
}
