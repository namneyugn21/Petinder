package com.petinder.service;

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

public interface PetService {
    CreatePetOutput createPet(CreatePetInput createPetInput);

    ReadPetOutput readPet(ReadPetInput readPetInput);

    ListPetOutput listPet(ListPetInput listPetInput);

    UpdatePetOutput updatePet(UpdatePetInput updatePetInput);

    DeletePetOutput deletePet(DeletePetInput deletePetInput);
}
