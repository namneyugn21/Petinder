package com.petinder.userservice.service;

import com.petinder.userservice.dto.comm.ReadPetOutput;
import com.petinder.userservice.model.UserPet;

import java.util.List;
import java.util.UUID;

public interface PetService {
    List<ReadPetOutput> getPets(List<UUID> petIds);

    boolean checkPets(List<UUID> petIds);

    void likePet(final UserPet userPet);

    void dislikePet(final UserPet userPet);
}
