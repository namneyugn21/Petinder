package com.petinder.userservice.dto.pet;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ListUserPetOutput {
    private List<ReadPetOutput> pets;
    private int nextPage;
    private int nextSize;
    private int totalPage;
}
