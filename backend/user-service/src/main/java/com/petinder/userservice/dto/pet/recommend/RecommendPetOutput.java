package com.petinder.userservice.dto.pet.recommend;

import com.petinder.userservice.dto.comm.ReadPetOutput;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class RecommendPetOutput {
    List<ReadPetOutput> pets;
}
