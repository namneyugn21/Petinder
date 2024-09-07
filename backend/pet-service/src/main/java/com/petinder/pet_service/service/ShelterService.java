package com.petinder.pet_service.service;

import com.petinder.pet_service.dto.shelter.create.CreateShelterInput;
import com.petinder.pet_service.dto.shelter.create.CreateShelterOutput;
import com.petinder.pet_service.dto.shelter.list.ListShelterInput;
import com.petinder.pet_service.dto.shelter.list.ListShelterOutput;
import com.petinder.pet_service.dto.shelter.read.ReadShelterInput;
import com.petinder.pet_service.dto.shelter.read.ReadShelterOutput;
import com.petinder.pet_service.dto.shelter.update.UpdateShelterInput;
import com.petinder.pet_service.dto.shelter.update.UpdateShelterOutput;

public interface ShelterService {
    CreateShelterOutput createShelter(final CreateShelterInput input);

    UpdateShelterOutput updateShelter(final UpdateShelterInput input);

    ReadShelterOutput readShelter(final ReadShelterInput input);

    ListShelterOutput listShelter(final ListShelterInput input);
}
