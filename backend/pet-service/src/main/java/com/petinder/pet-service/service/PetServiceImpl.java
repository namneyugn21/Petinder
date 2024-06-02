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
import com.petinder.exception.PetNotFound;
import com.petinder.mapper.PetMapper;
import com.petinder.model.Pet;
import com.petinder.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements com.petinder.service.PetService {
    private final PetMapper petMapper;
    private final PetRepository petRepository;

    public CreatePetOutput createPet(CreatePetInput createPetInput) {
        Pet pet = petMapper.createPetInputToPet(createPetInput);
        pet = petRepository.save(pet);
        return petMapper.petToCreatePetOutput(pet);
    }

    public ReadPetOutput readPet(ReadPetInput readPetInput) {
        Pet pet = findPet(readPetInput.getPetId());
        return petMapper.petToReadPetOutput(pet);
    }

    public ListPetOutput listPet(ListPetInput listPetInput) {
        Page<Pet> pets = petRepository.findAll(listPetInput.getPageable());

        int size = pets.getSize();
        int totalPage = pets.getTotalPages();
        int nextPage = totalPage > 0 ? (pets.getNumber() + 1) % totalPage : 0;
        List<ReadPetOutput> readPetOutputs = pets.getContent()
                .stream()
                .map(petMapper::petToReadPetOutput)
                .toList();
        return petMapper.toListPetOutput(readPetOutputs, nextPage, size, totalPage);
    }

    public UpdatePetOutput updatePet(UpdatePetInput updatePetInput) {
        Pet pet = findPet(updatePetInput.getId());
        petMapper.updatePetInputToPet(updatePetInput, pet);

        pet = petRepository.save(pet);
        return petMapper.petToUpdatePetOutput(pet);
    }

    public DeletePetOutput deletePet(DeletePetInput deletePetInput) {
        Pet pet = findPet(deletePetInput.getPetId());
        petRepository.delete(pet);
        return new DeletePetOutput();
    }

    private Pet findPet(String petId) {
        return petRepository.findById(petId)
                .orElseThrow(() -> new PetNotFound(petId));
    }
}
