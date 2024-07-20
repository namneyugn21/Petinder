package com.petinder.pet_service.dto.read;

import com.petinder.pet_service.model.PetProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class ReadPetOutput {
    private UUID id;
    private String name;
    private String picture;
    private PetProperty.Age age;
    private double weight;
    private String breed;
    private String furColor;
    private String eyeColor;
}
