package com.petinder.userservice.dto.comm;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateUserInput {
    private UUID accountId;

    private String email;

    private String firstName;

    private String middleName;

    private String lastName;

    private String picture;

    private String phoneNumber;

    private String description;
}
