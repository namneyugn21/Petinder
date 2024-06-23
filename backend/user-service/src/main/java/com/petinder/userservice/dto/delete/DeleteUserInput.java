package com.petinder.userservice.dto.delete;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class DeleteUserInput {
    private UUID userId;
}
