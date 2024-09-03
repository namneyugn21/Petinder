package com.petinder.userservice.dto.pet.recommend;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class RecommendPetInput {
    UUID userId;
}
