package com.petinder.userservice.dto.pet;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@Data
@Builder
public class ListUserPetInput {
    private UUID userId;
    private Pageable pageable;
}
