package com.petinder.userservice.dto.user.read;

import lombok.Data;

@Data
public class ReadUserOutput {
    private String id;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String picture;
}
