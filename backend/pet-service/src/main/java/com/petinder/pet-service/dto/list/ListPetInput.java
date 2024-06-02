package com.petinder.dto.list;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
@AllArgsConstructor
public class ListPetInput {
    private Pageable pageable;
}
