package com.petinder.userservice.dto.user.create;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateUserInput {
    @Email(message = "Email must be valid")
    private String email;

    @NotEmpty(message = "First name must not be empty")
    private String firstName;

    private String middleName;

    @NotEmpty(message = "Last name must not be empty")
    private String lastName;

    private String picture;
}
