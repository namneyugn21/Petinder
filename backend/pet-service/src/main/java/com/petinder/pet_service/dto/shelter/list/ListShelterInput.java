package com.petinder.pet_service.dto.shelter.list;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.domain.Pageable;

@Value
@Builder
public class ListShelterInput {
    Pageable pageable;
}
