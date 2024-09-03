package com.petinder.userservice.controller;

import com.petinder.userservice.dto.EmptyResponse;
import com.petinder.userservice.dto.ResponseDTO;
import com.petinder.userservice.dto.pet.like.LikePetInput;
import com.petinder.userservice.dto.pet.list.ListUserPetInput;
import com.petinder.userservice.dto.pet.list.ListUserPetOutput;
import com.petinder.userservice.dto.pet.recommend.RecommendPetInput;
import com.petinder.userservice.dto.pet.recommend.RecommendPetOutput;
import com.petinder.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("${spring.application.api-prefix}/pet")
@RequiredArgsConstructor
public class UserPetController {
    private final UserService userService;

    @GetMapping
    public ResponseDTO<RecommendPetOutput> recommendPet(
            @RequestParam UUID userId   // TODO: use JWT
    ) {
        final RecommendPetInput input = RecommendPetInput.builder()
                .userId(userId)
                .build();
        final RecommendPetOutput output = userService.recommendPet(input);

        return ResponseDTO.success(output);
    }

    @PostMapping("/pet/like")
    public ResponseDTO<EmptyResponse> likePet(
            @RequestParam UUID userId,  // TODO: use JWT
            @RequestBody LikePetInput input
    ) {
        input.setUserId(userId);

        final EmptyResponse output = userService.likePet(input);
        return ResponseDTO.success(output);
    }

    @GetMapping("/pet/like")
    public ResponseDTO<ListUserPetOutput> listLikePet(
            @RequestParam UUID userId,  // TODO: use JWT

            @ParameterObject
            @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        final ListUserPetInput input = ListUserPetInput.builder()
                .userId(userId)
                .pageable(pageable)
                .build();

        final ListUserPetOutput output = userService.listLikePet(input);
        return ResponseDTO.success(output);
    }

    @PostMapping("/pet/dislike")
    public ResponseDTO<EmptyResponse> dislikePet(
            @RequestParam UUID userId,  // TODO: use JWT
            @RequestBody LikePetInput input
    ) {
        input.setUserId(userId);

        final EmptyResponse output = userService.dislikePet(input);
        return ResponseDTO.success(output);
    }

    @GetMapping("/pet/dislike")
    public ResponseDTO<ListUserPetOutput> listDislikePet(
            @RequestParam UUID userId,  // TODO: use JWT

            @ParameterObject
            @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        final ListUserPetInput input = ListUserPetInput.builder()
                .userId(userId)
                .pageable(pageable)
                .build();

        final ListUserPetOutput output = userService.listDislikePet(input);
        return ResponseDTO.success(output);
    }
}
