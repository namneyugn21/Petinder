package com.petinder.auth_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    public static final String SHELTER_VALUE = "SHELTER";
    public static final Role SHELTER = new Role(SHELTER_VALUE);

    public static final String USER_VALUE = "USER";
    public static final Role USER = new Role(USER_VALUE);

    @Id
    private String name;
}
