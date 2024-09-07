package com.petinder.pet_service.repository;

import com.petinder.pet_service.model.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ShelterRepository extends JpaRepository<Shelter, UUID> {
}
