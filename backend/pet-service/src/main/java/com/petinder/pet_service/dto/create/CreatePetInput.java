package com.petinder.pet_service.dto.create;

import com.petinder.pet_service.model.PetProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreatePetInput {
    @NotEmpty
    private String name;

    private String picture;

    @NotNull
    private PetProperty.Age age;

    @Min(0)
    private double weight;

    @NotEmpty
    private String breed;

    @NotEmpty
    private String furColor;

    @NotEmpty
    private String eyeColor;
}
