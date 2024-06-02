package com.petinder.dto.create;

import com.petinder.model.PetProperty;
import lombok.Data;

@Data
public class CreatePetOutput {
    private String id;
    private String name;
    private String picture;
    private PetProperty.Age age;
    private double weight;
    private String breed;
    private String furColor;
    private String eyeColor;
}
