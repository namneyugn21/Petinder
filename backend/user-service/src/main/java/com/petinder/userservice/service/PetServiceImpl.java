package com.petinder.userservice.service;

import com.petinder.userservice.dto.pet.ListUserPetInput;
import com.petinder.userservice.dto.pet.ListUserPetOutput;
import com.petinder.userservice.dto.pet.ReadPetOutput;
import com.petinder.userservice.dto.pet.RegisterPetInput;
import com.petinder.userservice.exception.UserNotFound;
import com.petinder.userservice.invoker.PetServiceInvoker;
import com.petinder.userservice.mapper.PetMapper;
import com.petinder.userservice.model.Pet;
import com.petinder.userservice.model.User;
import com.petinder.userservice.repository.PetRepository;
import com.petinder.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {
    private final PetMapper petMapper;
    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final PetServiceInvoker petServiceInvoker;

    @Override
    public void registerPet(RegisterPetInput input) {
        // Get user reference from User DB
        UUID userId = input.getUserId();
        if (!userRepository.existsById(userId)) {
            throw new UserNotFound(userId);
        }
        User user = userRepository.getReferenceById(userId);

        // Save pet
        Pet pet = petMapper.registerPetInputToPet(input);
        pet.setUser(user);
        petRepository.save(pet);
    }

    @Override
    public ListUserPetOutput listUserPet(ListUserPetInput input) {
        final UUID userId = input.getUserId();
        if (!userRepository.existsById(userId)) {
            throw new UserNotFound(userId);
        }

        // Get pets' details from Pet Service using list of pet ids
        Page<Pet> petPage = petRepository.findAllByUserId(userId, input.getPageable());
        List<UUID> petIds = petPage.get().map(Pet::getPetId).toList();
        CompletableFuture<Mono<List<ReadPetOutput>>> futurePets = petServiceInvoker.getPets(petIds);

        // Next page info
        Pageable nextPetPage = petPage.nextOrLastPageable();
        int nextPage = nextPetPage.getPageNumber();
        int nextSize = nextPetPage.getPageSize();
        int totalPage = petPage.getTotalPages();

        // Await pets' detail from Pet Service
        List<ReadPetOutput> pets;
        try {
            pets = futurePets
                    .get(30, TimeUnit.SECONDS)      // wait for api call to Pet Service to complete
                    .block(Duration.ofSeconds(30));         // wait for the flux from Pet Service to become list
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException("Can't get pet detail from Pet Service", e);
        }

        return ListUserPetOutput.builder()
                .pets(pets)
                .nextPage(nextPage)
                .nextSize(nextSize)
                .totalPage(totalPage)
                .build();
    }
}
