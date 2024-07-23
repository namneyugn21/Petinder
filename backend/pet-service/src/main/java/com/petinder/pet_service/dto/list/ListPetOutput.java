package com.petinder.pet_service.dto.list;

import com.petinder.pet_service.dto.read.ReadPetOutput;
import lombok.Data;

import java.util.List;

@Data
public class ListPetOutput {
    private List<ReadPetOutput> pets;
    private int nextPage;
    private int nextSize;
    private int totalPage;
}
