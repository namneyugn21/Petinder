package com.petinder.userservice.repository;

import com.petinder.userservice.model.Pet;
import com.petinder.userservice.model.PetKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PetRepository extends JpaRepository<Pet, PetKey> {
    Page<Pet> findAllByUserId(UUID userId, Pageable pageable);
}
