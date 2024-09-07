package com.petinder.pet_service.dto.shelter.list;

import com.petinder.pet_service.dto.shelter.read.ReadShelterOutput;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class ListShelterOutput {
    List<ReadShelterOutput> shelters;
    Integer nextPage;
    Integer size;
    Integer totalPage;
    Long totalElements;
}
