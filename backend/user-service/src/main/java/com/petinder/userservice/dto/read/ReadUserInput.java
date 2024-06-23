package com.petinder.userservice.dto.read;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ReadUserInput {
    private UUID userId;
}
