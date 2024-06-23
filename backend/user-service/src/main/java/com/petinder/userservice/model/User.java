package com.petinder.userservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@Entity(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "picture")
    private String picture;
}
