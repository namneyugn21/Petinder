package com.petinder.dto.list;

import com.petinder.dto.read.ReadPetOutput;
import lombok.Data;

import java.util.List;

@Data
public class ListPetOutput {
    private List<ReadPetOutput> pets;
    private int nextPage;
    private int size;
    private int totalPage;
}
