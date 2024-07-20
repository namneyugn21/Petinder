package com.petinder.userservice.mapper;

import com.petinder.userservice.dto.pet.RegisterPetInput;
import com.petinder.userservice.dto.pet.RegisterPetOutput;
import com.petinder.userservice.model.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PetMapper {
    @Mapping(target = "user", ignore = true)
    Pet registerPetInputToPet(RegisterPetInput input);

    RegisterPetOutput petToRegisterPetOutput(Pet pet);
}
