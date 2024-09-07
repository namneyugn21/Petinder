package com.petinder.pet_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class PetProperty implements Serializable {
    @Column(name = "picture")
    private String picture;

    @Column(name = "description")
    private String description;

    @Column(name = "age", nullable = false)
    private Age age;

    @Column(name = "weight", nullable = false)
    private double weight;

    @Column(name = "breed", nullable = false)
    private String breed;

    @Column(name = "fur_color", nullable = false)
    private String furColor;

    @Column(name = "eye_color", nullable = false)
    private String eyeColor;

    public enum Age {
        BABY, YOUNG, ADULT, SENIOR
    }
}
