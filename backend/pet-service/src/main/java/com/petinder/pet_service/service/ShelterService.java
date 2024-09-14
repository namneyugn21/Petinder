package com.petinder.pet_service.service;

import com.petinder.pet_service.dto.shelter.create.CreateShelterInput;
import com.petinder.pet_service.dto.shelter.create.CreateShelterOutput;
import com.petinder.pet_service.dto.shelter.list.ListShelterInput;
import com.petinder.pet_service.dto.shelter.list.ListShelterOutput;
import com.petinder.pet_service.dto.shelter.read.ReadShelterInput;
import com.petinder.pet_service.dto.shelter.read.ReadShelterOutput;
import com.petinder.pet_service.dto.shelter.update.UpdateShelterInput;
import com.petinder.pet_service.dto.shelter.update.UpdateShelterOutput;
import com.petinder.pet_service.model.Shelter;

import java.util.UUID;

public interface ShelterService {
    CreateShelterOutput createShelter(CreateShelterInput input);

    UpdateShelterOutput updateShelter(UpdateShelterInput input);

    ReadShelterOutput readShelter(ReadShelterInput input);

    ListShelterOutput listShelter(ListShelterInput input);

    /**
     * Get a reference to the {@link Shelter} entity with the given identifier.
     * The reference is {@code null} if there is no entity match the given identifier.
     *
     * @param shelterId identifier to find the reference
     * @return a reference to {@link Shelter} or null if one doesn't exist.
     */
    Shelter getShelterReferenceById(UUID shelterId);
}
