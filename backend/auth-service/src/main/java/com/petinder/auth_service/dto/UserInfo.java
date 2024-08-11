package com.petinder.auth_service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserInfo {
    private UUID accountId;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String picture;
}
