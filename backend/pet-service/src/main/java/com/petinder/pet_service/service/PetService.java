package com.petinder.pet_service.service;

import com.petinder.pet_service.dto.create.CreatePetInput;
import com.petinder.pet_service.dto.create.CreatePetOutput;
import com.petinder.pet_service.dto.delete.DeletePetInput;
import com.petinder.pet_service.dto.delete.DeletePetOutput;
import com.petinder.pet_service.dto.list.ListPetInput;
import com.petinder.pet_service.dto.list.ListPetOutput;
import com.petinder.pet_service.dto.read.ReadPetInput;
import com.petinder.pet_service.dto.read.ReadPetOutput;
import com.petinder.pet_service.dto.search.SearchPetInput;
import com.petinder.pet_service.dto.search.SearchPetOutput;
import com.petinder.pet_service.dto.update.UpdatePetInput;
import com.petinder.pet_service.dto.update.UpdatePetOutput;
import jakarta.annotation.Nullable;

import java.util.List;
import java.util.UUID;

public interface PetService {
    CreatePetOutput createPet(CreatePetInput createPetInput);

    ReadPetOutput readPet(ReadPetInput readPetInput);

    ListPetOutput listPet(ListPetInput listPetInput);

    UpdatePetOutput updatePet(UpdatePetInput updatePetInput);

    DeletePetOutput deletePet(DeletePetInput deletePetInput);

    List<ReadPetOutput> readPetBulk(List<UUID> petIds);

    Boolean checkPetBulk(List<UUID> petIds);

    List<ReadPetOutput> readPetAfter(@Nullable UUID afterPetId);

    SearchPetOutput searchPet(SearchPetInput input);
}
