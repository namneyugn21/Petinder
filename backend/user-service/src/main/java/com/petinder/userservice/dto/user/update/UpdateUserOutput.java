package com.petinder.userservice.dto.update;

import lombok.Data;

@Data
public class UpdateUserOutput {
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String picture;
}
