package com.petinder.pet_service.service;

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

public interface PetService {
    CreatePetOutput createPet(CreatePetInput createPetInput);

    ReadPetOutput readPet(ReadPetInput readPetInput);

    ListPetOutput listPet(ListPetInput listPetInput);

    UpdatePetOutput updatePet(UpdatePetInput updatePetInput);

    DeletePetOutput deletePet(DeletePetInput deletePetInput);
}
