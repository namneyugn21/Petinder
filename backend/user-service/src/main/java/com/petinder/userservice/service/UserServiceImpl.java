package com.petinder.userservice.service;

import com.petinder.userservice.config.RabbitMqConfig;
import com.petinder.userservice.dto.EmptyResponse;
import com.petinder.userservice.dto.comm.CreateUserInput;
import com.petinder.userservice.dto.comm.ReadPetOutput;
import com.petinder.userservice.dto.pet.like.LikePetInput;
import com.petinder.userservice.dto.pet.list.ListUserPetInput;
import com.petinder.userservice.dto.pet.list.ListUserPetOutput;
import com.petinder.userservice.dto.pet.recommend.RecommendPetInput;
import com.petinder.userservice.dto.pet.recommend.RecommendPetOutput;
import com.petinder.userservice.dto.user.delete.DeleteUserInput;
import com.petinder.userservice.dto.user.delete.DeleteUserOutput;
import com.petinder.userservice.dto.user.list.ListUserInput;
import com.petinder.userservice.dto.user.list.ListUserOutput;
import com.petinder.userservice.dto.user.read.ReadUserInput;
import com.petinder.userservice.dto.user.read.ReadUserOutput;
import com.petinder.userservice.dto.user.update.UpdateUserInput;
import com.petinder.userservice.dto.user.update.UpdateUserOutput;
import com.petinder.userservice.exception.black.PetNotFound;
import com.petinder.userservice.exception.white.UserNotFound;
import com.petinder.userservice.mapper.UserMapper;
import com.petinder.userservice.model.User;
import com.petinder.userservice.model.UserPet;
import com.petinder.userservice.repository.UserPetRepository;
import com.petinder.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final PetService petService;
    private final UserRepository userRepository;
    private final UserPetRepository userPetRepository;

    @Override
    @RabbitListener(queues = RabbitMqConfig.CREATE_USER)
    public void createUser(
            CreateUserInput input
    ) {
        final User user = userMapper.createUserInputToUser(input);
        userRepository.save(user);
    }

    @Override
    public ReadUserOutput readUser(
            ReadUserInput input
    ) {
        User user = userRepository.findById(input.getUserId())
                .orElseThrow(() -> new UserNotFound(input.getUserId()));
        return userMapper.userToReadUserOutput(user);
    }

    @Override
    public UpdateUserOutput updateUser(
            UpdateUserInput input
    ) {
        User user = userRepository.findById(input.getUserId())
                .orElseThrow(() -> new UserNotFound(input.getUserId()));
        user = userMapper.updateUserInputToUser(input, user);
        user = userRepository.save(user);
        return userMapper.userToUpdateUserOutput(user);
    }

    @Override
    public DeleteUserOutput deleteUser(
            DeleteUserInput input
    ) {
        if (!userRepository.existsById(input.getUserId())) {
            throw new UserNotFound(input.getUserId());
        }

        userRepository.deleteById(input.getUserId());
        return new DeleteUserOutput();
    }

    @Override
    public ListUserOutput listUser(
            ListUserInput input
    ) {
        final Page<User> userPage = userRepository.findAll(input.getPageable());

        // Users output
        final List<ReadUserOutput> users = userPage.getContent()
                .stream()
                .map(userMapper::userToReadUserOutput)
                .toList();

        final Pageable nextUserPage = userPage.nextOrLastPageable();
        return ListUserOutput.builder()
                .users(users)
                .nextPage(nextUserPage.getPageNumber())
                .size(userPage.getSize())
                .totalPage(userPage.getTotalPages())
                .totalElements(userPage.getTotalElements())
                .build();
    }

    @Override
    public RecommendPetOutput recommendPet(final RecommendPetInput input) {
        final UUID userId = input.getUserId();
        if (!userRepository.existsById(userId)) {
            throw new UserNotFound(userId);
        }

        // TODO: perform better recommendation.
        //  Possibly using current user's location as well as some filtering parameters
        UUID lastPetId = null;
        final Optional<UserPet> userPetOption = userPetRepository.findFirstByOrderByCreateAtDesc();
        if (userPetOption.isPresent()) {
            lastPetId = userPetOption.get().getPetId();
        }

        final List<ReadPetOutput> pets = petService.getPetsAfter(lastPetId);
        return RecommendPetOutput.builder()
                .pets(pets)
                .build();
    }

    @Override
    @Transactional
    public EmptyResponse likePet(final LikePetInput input) {
        final UUID userId = input.getUserId();
        if (!userRepository.existsById(userId)) {
            throw new UserNotFound(userId);
        }

        // Save pet to DB
        if (userPetRepository.existsByUserIdAndPetId(input.getUserId(), input.getPetId())) {
            log.warn("Duplicate like. Pet is recommended twice to the same user!");
            return new EmptyResponse();
        }
        if (!petService.checkPets(List.of(input.getPetId()))) {
            throw new PetNotFound(input.getPetId());
        }
        final UserPet userPet = UserPet.builder()
                .userId(input.getUserId())
                .petId(input.getPetId())
                .liked(Boolean.TRUE)
                .build();
        userPetRepository.saveAndFlush(userPet);    // flush to make sure any DB errors will be thrown now

        // Send it to RabbitMQ
        petService.likePet(userPet);

        return new EmptyResponse();
    }

    @Override
    public ListUserPetOutput listLikePet(ListUserPetInput input) {
        final UUID userId = input.getUserId();
        if (!userRepository.existsById(userId)) {
            throw new UserNotFound(userId);
        }

        // Get a list of liked petIds
        final Page<UserPet> petPage = userPetRepository.findAllByUserIdAndLiked(
                userId,
                true,
                input.getPageable()
        );
        final List<UUID> petIds = petPage.getContent()
                .stream()
                .map(UserPet::getPetId)
                .toList();

        // Get more pet information from Pet Service
        final List<ReadPetOutput> pets;
        if (!petIds.isEmpty()) {
            pets = petService.getPets(petIds);
            pets.forEach(pet -> pet.setIsLike(true));
        } else {
            pets = List.of();
        }

        final Pageable nextPetPage = petPage.nextOrLastPageable();
        return ListUserPetOutput.builder()
                .pets(pets)
                .nextPage(nextPetPage.getPageNumber())
                .nextSize(nextPetPage.getPageSize())
                .totalPage(petPage.getTotalPages())
                .totalElements(petPage.getTotalElements())
                .build();
    }

    @Override
    @Transactional
    public EmptyResponse dislikePet(final LikePetInput input) {
        final UUID userId = input.getUserId();
        if (!userRepository.existsById(userId)) {
            throw new UserNotFound(userId);
        }

        // Save pet to DB
        if (userPetRepository.existsByUserIdAndPetId(input.getUserId(), input.getPetId())) {
            log.warn("Duplicate dislike. Pet is recommended twice to the same user!");
            return new EmptyResponse();
        }
        if (!petService.checkPets(List.of(input.getPetId()))) {
            throw new PetNotFound(input.getPetId());
        }
        final UserPet userPet = UserPet.builder()
                .userId(input.getUserId())
                .petId(input.getPetId())
                .liked(Boolean.FALSE)
                .build();
        userPetRepository.saveAndFlush(userPet);

        // Send it to RabbitMQ
        petService.dislikePet(userPet);

        return new EmptyResponse();
    }

    @Override
    public ListUserPetOutput listDislikePet(ListUserPetInput input) {
        final UUID userId = input.getUserId();
        if (!userRepository.existsById(userId)) {
            throw new UserNotFound(userId);
        }

        // Get a list of liked petIds
        final Page<UserPet> petPage = userPetRepository.findAllByUserIdAndLiked(
                userId,
                false,
                input.getPageable()
        );
        final List<UUID> petIds = petPage.getContent()
                .stream()
                .map(UserPet::getPetId)
                .toList();

        // Get more pet information from Pet Service
        final List<ReadPetOutput> pets;
        if (!petIds.isEmpty()) {
            pets = petService.getPets(petIds);
            pets.forEach(pet -> pet.setIsLike(false));
        } else {
            pets = List.of();
        }

        final Pageable nextPetPage = petPage.nextOrLastPageable();
        return ListUserPetOutput.builder()
                .pets(pets)
                .nextPage(nextPetPage.getPageNumber())
                .nextSize(nextPetPage.getPageSize())
                .totalPage(petPage.getTotalPages())
                .totalElements(petPage.getTotalElements())
                .build();
    }

    @Override
    public Boolean check(List<UUID> userIds) {
        return userRepository.findAllById(userIds).size() == userIds.size();
    }
}
