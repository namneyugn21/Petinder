package com.petinder.pet_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class PetProperty {
    @Column(nullable = false)
    private Age age;

    @Column(nullable = false)
    private double weight;

    @Column(nullable = false)
    private String breed;

    @Column(nullable = false)
    private String furColor;

    @Column(nullable = false)
    private String eyeColor;

    public enum Age {
        BABY, YOUNG, ADULT, SENIOR
    }
}
