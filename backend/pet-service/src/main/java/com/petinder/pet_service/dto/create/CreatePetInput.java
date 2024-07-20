package com.petinder.pet_service.dto.create;

import com.petinder.pet_service.model.PetProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CreatePetInput {
    @NotEmpty(message = "Pet's name cannot be empty")
    private String name;

    @NotNull(message = "Pet must be own by someone, either a shelter or a user")
    private UUID ownerId;

    private String picture;

    @NotNull
    private PetProperty.Age age;

    @Min(value = 0, message = "Pet's weight cannot be less than 0")
    private double weight;

    @NotEmpty(message = "Pet's breed cannot be empty")
    private String breed;

    @NotEmpty(message = "Pet's fur color cannot be empty")
    private String furColor;

    @NotEmpty(message = "Pet's eye color cannot be empty")
    private String eyeColor;
}
