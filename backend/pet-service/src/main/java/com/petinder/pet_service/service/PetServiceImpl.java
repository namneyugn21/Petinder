package com.petinder.pet_service.service;

import com.petinder.pet_service.config.RabbitMqConfig;
import com.petinder.pet_service.dto.comm.UserPetDto;
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
import com.petinder.pet_service.exception.PetNotFound;
import com.petinder.pet_service.exception.ShelterNotFound;
import com.petinder.pet_service.mapper.PetMapper;
import com.petinder.pet_service.model.Pet;
import com.petinder.pet_service.model.Shelter;
import com.petinder.pet_service.repository.PetRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {
    private final PetMapper petMapper;
    private final PetRepository petRepository;
    private final ShelterService shelterService;

    @Transactional
    public CreatePetOutput createPet(CreatePetInput createPetInput) {
        Pet pet = petMapper.createPetInputToPet(createPetInput);
        pet.setStatus(Pet.Status.FREE);

        // Get shelter reference for Many-To-One mapping
        final Shelter shelterReference = shelterService.getShelterReferenceById(createPetInput.getShelterId());
        if (shelterReference == null) {
            throw new ShelterNotFound(createPetInput.getShelterId());
        }
        pet.setShelter(shelterReference);

        pet = petRepository.save(pet);
        return petMapper.petToCreatePetOutput(pet);
    }

    @Override
    public ReadPetOutput readPet(ReadPetInput readPetInput) {
        Pet pet = findPet(readPetInput.getPetId());
        return petMapper.petToReadPetOutput(pet);
    }

    @Override
    public ListPetOutput listPet(ListPetInput listPetInput) {
        Page<Pet> pets = petRepository.findAll(listPetInput.getPageable());

        Pageable nextPageable = pets.nextOrLastPageable();
        int size = nextPageable.getPageSize();
        int totalPage = pets.getTotalPages();
        int nextPage = nextPageable.getPageNumber();

        List<ReadPetOutput> readPetOutputs = pets.getContent()
                .stream()
                .map(petMapper::petToReadPetOutput)
                .toList();
        return petMapper.toListPetOutput(readPetOutputs, nextPage, size, totalPage);
    }

    @Override
    public UpdatePetOutput updatePet(UpdatePetInput updatePetInput) {
        Pet pet = findPet(updatePetInput.getId());
        petMapper.updatePetInputToPet(updatePetInput, pet);

        pet = petRepository.save(pet);
        return petMapper.petToUpdatePetOutput(pet);
    }

    @Override
    public DeletePetOutput deletePet(DeletePetInput deletePetInput) {
        Pet pet = findPet(deletePetInput.getPetId());
        petRepository.delete(pet);
        return new DeletePetOutput();
    }

    private Pet findPet(UUID petId) {
        return petRepository.findById(petId)
                .orElseThrow(() -> new PetNotFound(petId));
    }

    @Override
    public List<ReadPetOutput> readPetBulk(List<UUID> petIds) {
        final List<Pet> pets;
        if (petIds == null) {
            pets = petRepository.findAll();
        } else {
            pets = petRepository.findAllById(petIds);
        }
        return pets.stream()
                .map(petMapper::petToReadPetOutput)
                .toList();
    }

    @Override
    public Boolean checkPetBulk(List<UUID> petIds) {
        return petRepository.findAllById(petIds).size() == petIds.size();
    }

    @Override
    @Transactional
    public List<ReadPetOutput> readPetAfter(UUID afterPetId) {
        final int LIMIT = 10;
        final Stream<Pet> result;
        if (afterPetId == null) {
            result = petRepository.findTopByCreateAt(LIMIT);
        } else {
            result = petRepository.findTopByCreateAtAfter(afterPetId, LIMIT);
        }
        return result.map(petMapper::petToReadPetOutput).toList();
    }

    @Override
    @Transactional
    public SearchPetOutput searchPet(SearchPetInput input) {
        List<ReadPetOutput> pets = petRepository.searchByKeyword(input.getKeyword(), input.getLimit())
                .map(petMapper::petToReadPetOutput)
                .toList();
        return new SearchPetOutput(pets);
    }

    @RabbitListener(queues = RabbitMqConfig.LIKE_PET)
    public void like(UserPetDto in) {
        log.info("{} LIKE {}", in.getUserId(), in.getPetId());
    }

    @RabbitListener(queues = RabbitMqConfig.DISLIKE_PET)
    public void dislike(UserPetDto in) {
        log.info("{} DISLIKE {}", in.getUserId(), in.getPetId());
    }
}
