package com.petinder.pet_service.controller;

import com.petinder.pet_service.dto.ResponseDTO;
import com.petinder.pet_service.dto.create.CreatePetInput;
import com.petinder.pet_service.dto.create.CreatePetOutput;
import com.petinder.pet_service.dto.delete.DeletePetInput;
import com.petinder.pet_service.dto.delete.DeletePetOutput;
import com.petinder.pet_service.dto.list.ListPetInput;
import com.petinder.pet_service.dto.list.ListPetOutput;
import com.petinder.pet_service.dto.read.ReadPetInput;
import com.petinder.pet_service.dto.read.ReadPetOutput;
import com.petinder.pet_service.dto.update.UpdatePetInput;
import com.petinder.pet_service.dto.update.UpdatePetOutput;
import com.petinder.pet_service.service.PetService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${spring.application.api-prefix}")
@RequiredArgsConstructor
public class PetController {
    private final PetService petService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDTO<CreatePetOutput> createPet(
            @RequestBody @Validated CreatePetInput createPetInput
    ) {
        CreatePetOutput createPetOutput = petService.createPet(createPetInput);
        return ResponseDTO.success(createPetOutput);
    }

    @GetMapping("/{petId}")
    public ResponseDTO<ReadPetOutput> readPet(
            @PathVariable UUID petId
    ) {
        ReadPetInput readPetInput = new ReadPetInput(petId);
        ReadPetOutput readPetOutput = petService.readPet(readPetInput);
        return ResponseDTO.success(readPetOutput);
    }

    @GetMapping
    public ResponseDTO<ListPetOutput> listPet(
            @ParameterObject @PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        ListPetInput listPetInput = new ListPetInput(pageable);
        ListPetOutput listPetOutput = petService.listPet(listPetInput);
        return ResponseDTO.success(listPetOutput);
    }

    @PutMapping("/{petId}")
    public ResponseDTO<UpdatePetOutput> updatePet(
            @PathVariable UUID petId,
            @RequestBody @Validated UpdatePetInput updatePetInput
    ) {
        updatePetInput.setId(petId);
        UpdatePetOutput updatePetOutput = petService.updatePet(updatePetInput);
        return ResponseDTO.success(updatePetOutput);
    }

    @DeleteMapping("/{petId}")
    public ResponseDTO<DeletePetOutput> deletePet(
            @PathVariable UUID petId
    ) {
        DeletePetInput deletePetInput = new DeletePetInput(petId);
        DeletePetOutput deletePetOutput = petService.deletePet(deletePetInput);
        return ResponseDTO.success(deletePetOutput);
    }

    @Hidden
    @GetMapping("/internal")
    public List<ReadPetOutput> readPetBulk(
            @RequestParam(required = false) List<UUID> petIds
    ) {
        return petService.readPetBulk(petIds);
    }

    @Hidden
    @GetMapping("/internal/from")
    public List<ReadPetOutput> readPetAfter(
            @RequestParam(required = false) UUID id
    ) {
        return petService.readPetAfter(id);
    }

    @Hidden
    @GetMapping("/internal/check")
    public Boolean checkPetBulk(
            @RequestParam List<UUID> petIds
    ) {
        return petService.checkPetBulk(petIds);
    }
}
