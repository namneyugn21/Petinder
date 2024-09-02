package com.petinder.userservice.dto.pet.like;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.Value;
import lombok.With;
import lombok.experimental.NonFinal;

import java.util.UUID;

@Value
public class LikePetInput {
    @NotNull
    UUID petId;

    @Setter
    @NonFinal
    @JsonIgnore
    @With(AccessLevel.PRIVATE)
    UUID userId;
}
