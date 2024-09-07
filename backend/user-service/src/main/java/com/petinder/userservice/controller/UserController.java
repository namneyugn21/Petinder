package com.petinder.userservice.controller;

import com.petinder.userservice.dto.ResponseDTO;
import com.petinder.userservice.dto.user.delete.DeleteUserInput;
import com.petinder.userservice.dto.user.delete.DeleteUserOutput;
import com.petinder.userservice.dto.user.list.ListUserInput;
import com.petinder.userservice.dto.user.list.ListUserOutput;
import com.petinder.userservice.dto.user.read.ReadUserInput;
import com.petinder.userservice.dto.user.read.ReadUserOutput;
import com.petinder.userservice.dto.user.update.UpdateUserInput;
import com.petinder.userservice.dto.user.update.UpdateUserOutput;
import com.petinder.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${spring.application.api-prefix}")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

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

    @Hidden
    @GetMapping("/internal/check")
    public Boolean check(
            @RequestParam List<UUID> userIds
    ) {
        return userService.check(userIds);
    }
}
