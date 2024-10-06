package com.petinder.userservice.dto.comm;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class ReadPetOutput {
    private String id;
    private String name;
    private String picture;
    private String age;
    private Double weight;
    private String breed;
    private String furColor;
    private String eyeColor;
    private String description;

    // Filled by THIS service
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isLike;
}
