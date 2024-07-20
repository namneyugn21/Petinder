package com.petinder.userservice.controller;

import com.petinder.userservice.dto.ResponseDTO;
import com.petinder.userservice.dto.pet.ListUserPetInput;
import com.petinder.userservice.dto.pet.ListUserPetOutput;
import com.petinder.userservice.dto.pet.RegisterPetInput;
import com.petinder.userservice.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("${spring.application.api-prefix}/{userId}")
public class PetController {
    private final PetService petService;

    @Operation(summary = "Internal: Pet Service will invoke this endpoint when a new pet is created")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/pet")
    public void registerPet(
            @PathVariable UUID userId,
            @RequestBody RegisterPetInput input
    ) {
        input.setUserId(userId);
        petService.registerPet(input);
    }

    @GetMapping("/pet")
    public ResponseDTO<ListUserPetOutput> listUserPet(
            @PathVariable UUID userId,

            @ParameterObject
            @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        ListUserPetInput input = ListUserPetInput.builder()
                .userId(userId)
                .pageable(pageable)
                .build();
        ListUserPetOutput output = petService.listUserPet(input);
        return ResponseDTO.success(output);
    }
}
