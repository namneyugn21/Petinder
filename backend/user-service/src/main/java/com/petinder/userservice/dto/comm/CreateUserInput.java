package com.petinder.userservice.dto.comm;

import lombok.Data;

@Data
public class CreateUserInput {
    private String email;

    private String firstName;

    private String middleName;

    private String lastName;

    private String picture;

    private String phoneNumber;

    private String description;
}
