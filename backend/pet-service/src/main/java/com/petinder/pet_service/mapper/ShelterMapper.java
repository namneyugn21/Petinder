package com.petinder.pet_service.mapper;

import com.petinder.pet_service.dto.shelter.create.CreateShelterInput;
import com.petinder.pet_service.dto.shelter.create.CreateShelterOutput;
import com.petinder.pet_service.dto.shelter.read.ReadShelterOutput;
import com.petinder.pet_service.dto.shelter.update.UpdateShelterInput;
import com.petinder.pet_service.dto.shelter.update.UpdateShelterOutput;
import com.petinder.pet_service.model.Shelter;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ShelterMapper {
    Shelter createShelterInputToShelter(final CreateShelterInput input);

    CreateShelterOutput shelterToCreateShelterOutput(final Shelter shelter);

    void updateShelterFromUpdateShelterInput(@MappingTarget final Shelter shelter, final UpdateShelterInput input);

    UpdateShelterOutput shelterToUpdateShelterOutput(final Shelter shelter);

    ReadShelterOutput shelterToReadShelterOutput(final Shelter shelter);
}
