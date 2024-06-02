package com.petinder.pet_service.dto.update;

import com.petinder.pet_service.model.PetProperty;
import lombok.Data;

@Data
public class UpdatePetOutput {
    private String id;
    private String name;
    private String picture;
    private PetProperty.Age age;
    private double weight;
    private String breed;
    private String furColor;
    private String eyeColor;
}
