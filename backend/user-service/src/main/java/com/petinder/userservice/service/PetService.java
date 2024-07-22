package com.petinder.userservice.service;

import com.petinder.userservice.dto.pet.ListUserPetInput;
import com.petinder.userservice.dto.pet.ListUserPetOutput;
import com.petinder.userservice.dto.pet.RegisterPetInput;

public interface PetService {
    void registerPet(RegisterPetInput input);

    ListUserPetOutput listUserPet(ListUserPetInput input);
}
