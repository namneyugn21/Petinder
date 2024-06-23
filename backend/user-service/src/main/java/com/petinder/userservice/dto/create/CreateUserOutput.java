package com.petinder.userservice.dto.create;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateUserOutput {
    private UUID userId;
}
