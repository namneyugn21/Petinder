package com.petinder.pet_service.dto.search;

import com.petinder.pet_service.dto.read.ReadPetOutput;
import lombok.Value;

import java.util.List;

@Value
public class SearchPetOutput {
    List<ReadPetOutput> pets;
}
