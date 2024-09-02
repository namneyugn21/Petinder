package com.petinder.userservice.dto.pet.recommend;

import lombok.Value;

import java.util.UUID;

@Value
public class RecommendPetInput {
    UUID userId;
}
