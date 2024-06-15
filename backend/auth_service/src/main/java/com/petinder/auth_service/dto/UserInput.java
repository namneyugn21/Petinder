package com.petinder.auth_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInput {
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String picture;
}
