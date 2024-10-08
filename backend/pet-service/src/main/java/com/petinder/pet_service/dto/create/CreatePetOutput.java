package com.petinder.pet_service.dto.create;

import com.petinder.pet_service.model.PetProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class CreatePetOutput {
    private UUID id;
    private String name;
    private String picture;
    private String description;
    private PetProperty.Age age;
    private double weight;
    private String breed;
    private String furColor;
    private String eyeColor;
}
