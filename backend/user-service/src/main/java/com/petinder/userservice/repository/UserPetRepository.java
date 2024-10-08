package com.petinder.userservice.repository;

import com.petinder.userservice.model.User;
import com.petinder.userservice.model.UserPet;
import com.petinder.userservice.model.UserPetKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;


public interface UserPetRepository extends JpaRepository<UserPet, UserPetKey> {
    Page<UserPet> findAllByUserId(UUID userId, Pageable pageable);

    Page<UserPet> findAllByUserIdAndLiked(UUID userId, boolean liked, Pageable pageable);

    boolean existsByUserIdAndPetId(UUID userId, UUID petId);

    Optional<UserPet> findFirstByUserIdOrderByCreateAtDesc(UUID userId);

    Optional<UserPet> findByUserIdAndPetId(UUID userId, UUID petId);
}
