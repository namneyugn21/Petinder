package com.petinder.userservice.dto.pet.list;

import com.petinder.userservice.dto.comm.ReadPetOutput;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ListUserPetOutput {
    private List<ReadPetOutput> pets;
    private Integer nextPage;
    private Integer nextSize;
    private Integer totalPage;
    private Long totalElements;
}
