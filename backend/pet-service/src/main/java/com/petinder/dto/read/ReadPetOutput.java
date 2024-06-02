package com.petinder.dto.read;

import com.petinder.model.PetProperty;
import lombok.Data;

@Data
public class ReadPetOutput {
    private String id;
    private String name;
    private String picture;
    private PetProperty.Age age;
    private double weight;
    private String breed;
    private String furColor;
    private String eyeColor;
}
