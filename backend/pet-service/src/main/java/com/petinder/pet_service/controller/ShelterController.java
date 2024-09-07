package com.petinder.pet_service.controller;

import com.petinder.pet_service.dto.ResponseDTO;
import com.petinder.pet_service.dto.shelter.create.CreateShelterInput;
import com.petinder.pet_service.dto.shelter.create.CreateShelterOutput;
import com.petinder.pet_service.dto.shelter.list.ListShelterInput;
import com.petinder.pet_service.dto.shelter.list.ListShelterOutput;
import com.petinder.pet_service.dto.shelter.read.ReadShelterInput;
import com.petinder.pet_service.dto.shelter.read.ReadShelterOutput;
import com.petinder.pet_service.dto.shelter.update.UpdateShelterInput;
import com.petinder.pet_service.dto.shelter.update.UpdateShelterOutput;
import com.petinder.pet_service.service.ShelterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shelter")
public class ShelterController {
    private final ShelterService shelterService;

    @PostMapping
    public ResponseDTO<CreateShelterOutput> createShelter(
            @Valid @RequestBody final CreateShelterInput input
    ) {
        final CreateShelterOutput output = shelterService.createShelter(input);
        return ResponseDTO.success(output);
    }

    @PutMapping
    public ResponseDTO<UpdateShelterOutput> updateShelter(
            @RequestBody final UpdateShelterInput input
    ) {
        final UpdateShelterOutput output = shelterService.updateShelter(input);
        return ResponseDTO.success(output);
    }

    @GetMapping
    public ResponseDTO<ListShelterOutput> listShelter(
            @ParameterObject
            @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) final Pageable pageable
    ) {
        final ListShelterInput input = ListShelterInput.builder()
                .pageable(pageable)
                .build();
        final ListShelterOutput output = shelterService.listShelter(input);
        return ResponseDTO.success(output);
    }

    @GetMapping("/{shelterId}")
    public ResponseDTO<ReadShelterOutput> readShelter(
            @PathVariable final UUID shelterId
    ) {
        final ReadShelterInput input = ReadShelterInput.builder()
                .id(shelterId)
                .build();
        final ReadShelterOutput output = shelterService.readShelter(input);
        return ResponseDTO.success(output);
    }
}
