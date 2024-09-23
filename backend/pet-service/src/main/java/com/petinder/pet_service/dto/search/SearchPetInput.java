package com.petinder.pet_service.dto.search;

import lombok.Value;

@Value
public class SearchPetInput {
    String keyword;
    Long limit;
}
