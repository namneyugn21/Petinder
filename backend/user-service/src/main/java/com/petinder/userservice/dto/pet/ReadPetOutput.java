package com.petinder.userservice.dto.pet;

import lombok.Data;

@Data
public class ReadPetOutput {
    private String id;
    private String name;
    private String picture;
    private String age;
    private Double weight;
    private String breed;
    private String furColor;
    private String eyeColor;
}
