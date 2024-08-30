package com.petinder.userservice.controller;

import com.petinder.userservice.dto.EmptyResponse;
import com.petinder.userservice.dto.ResponseDTO;
import com.petinder.userservice.dto.pet.like.LikePetInput;
import com.petinder.userservice.dto.pet.list.ListUserPetInput;
import com.petinder.userservice.dto.pet.list.ListUserPetOutput;
import com.petinder.userservice.dto.user.create.CreateUserInput;
import com.petinder.userservice.dto.user.create.CreateUserOutput;
import com.petinder.userservice.dto.user.delete.DeleteUserInput;
import com.petinder.userservice.dto.user.delete.DeleteUserOutput;
import com.petinder.userservice.dto.user.list.ListUserInput;
import com.petinder.userservice.dto.user.list.ListUserOutput;
import com.petinder.userservice.dto.user.read.ReadUserInput;
import com.petinder.userservice.dto.user.read.ReadUserOutput;
import com.petinder.userservice.dto.user.update.UpdateUserInput;
import com.petinder.userservice.dto.user.update.UpdateUserOutput;
import com.petinder.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("${spring.application.api-prefix}")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseDTO<CreateUserOutput> createUser(
            @Valid @RequestBody CreateUserInput input
    ) {
        CreateUserOutput output = userService.createUser(input);
        return ResponseDTO.success(output);
    }

    @GetMapping("/{userId}")
    public ResponseDTO<ReadUserOutput> readUser(
            @PathVariable UUID userId
    ) {
        ReadUserInput input = ReadUserInput.builder()
                .userId(userId)
                .build();
        ReadUserOutput output = userService.readUser(input);
        return ResponseDTO.success(output);
    }

    @PutMapping("/{userId}")
    public ResponseDTO<UpdateUserOutput> updateUser(
            @PathVariable UUID userId,
            @RequestBody UpdateUserInput input
    ) {
        input.setUserId(userId);
        UpdateUserOutput output = userService.updateUser(input);
        return ResponseDTO.success(output);
    }

    @DeleteMapping("/{userId}")
    public ResponseDTO<DeleteUserOutput> deleteUser(
            @PathVariable UUID userId
    ) {
        DeleteUserInput input = DeleteUserInput.builder()
                .userId(userId)
                .build();
        DeleteUserOutput output = userService.deleteUser(input);
        return ResponseDTO.success(output);
    }

    @GetMapping
    public ResponseDTO<ListUserOutput> listUser(
            @ParameterObject
            @PageableDefault(sort = "email", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        ListUserInput input = ListUserInput.builder()
                .pageable(pageable)
                .build();
        ListUserOutput output = userService.listUser(input);
        return ResponseDTO.success(output);
    }

    /* PET RELATED ENDPOINTS */

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
