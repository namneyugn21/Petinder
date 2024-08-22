package com.petinder.userservice.dto.user.update;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.UUID;

@Data
public class UpdateUserInput {
    @JsonIgnore
    private UUID userId;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String picture;
    private String phoneNumber;
    private String description;
}
