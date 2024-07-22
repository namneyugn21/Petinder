package com.petinder.userservice.dto.user.create;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateUserOutput {
    private UUID userId;
}
