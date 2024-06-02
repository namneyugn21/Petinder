package com.petinder.controller;

import com.petinder.dto.ResponseDTO;
import com.petinder.dto.create.CreatePetInput;
import com.petinder.dto.create.CreatePetOutput;
import com.petinder.dto.delete.DeletePetInput;
import com.petinder.dto.delete.DeletePetOutput;
import com.petinder.dto.list.ListPetInput;
import com.petinder.dto.list.ListPetOutput;
import com.petinder.dto.read.ReadPetInput;
import com.petinder.dto.read.ReadPetOutput;
import com.petinder.dto.update.UpdatePetInput;
import com.petinder.dto.update.UpdatePetOutput;
import com.petinder.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pet")
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
            @PathVariable String petId
    ) {
        ReadPetInput readPetInput = new ReadPetInput(petId);
        ReadPetOutput readPetOutput = petService.readPet(readPetInput);
        return ResponseDTO.success(readPetOutput);
    }

    @GetMapping
    public ResponseDTO<ListPetOutput> listPet(
            @PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        ListPetInput listPetInput = new ListPetInput(pageable);
        ListPetOutput listPetOutput = petService.listPet(listPetInput);
        return ResponseDTO.success(listPetOutput);
    }

    @PutMapping("/{petId}")
    public ResponseDTO<UpdatePetOutput> updatePet(
            @PathVariable String petId,
            @RequestBody @Validated UpdatePetInput updatePetInput
    ) {
        updatePetInput.setId(petId);
        UpdatePetOutput updatePetOutput = petService.updatePet(updatePetInput);
        return ResponseDTO.success(updatePetOutput);
    }

    @DeleteMapping("/{petId}")
    public ResponseDTO<DeletePetOutput> deletePet(
            @PathVariable String petId
    ) {
        DeletePetInput deletePetInput = new DeletePetInput(petId);
        DeletePetOutput deletePetOutput = petService.deletePet(deletePetInput);
        return ResponseDTO.success(deletePetOutput);
    }
}
