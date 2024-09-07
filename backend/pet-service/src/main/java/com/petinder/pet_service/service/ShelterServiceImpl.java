package com.petinder.pet_service.service;

import com.petinder.pet_service.dto.shelter.create.CreateShelterInput;
import com.petinder.pet_service.dto.shelter.create.CreateShelterOutput;
import com.petinder.pet_service.dto.shelter.list.ListShelterInput;
import com.petinder.pet_service.dto.shelter.list.ListShelterOutput;
import com.petinder.pet_service.dto.shelter.read.ReadShelterInput;
import com.petinder.pet_service.dto.shelter.read.ReadShelterOutput;
import com.petinder.pet_service.dto.shelter.update.UpdateShelterInput;
import com.petinder.pet_service.dto.shelter.update.UpdateShelterOutput;
import com.petinder.pet_service.exception.ShelterNotFound;
import com.petinder.pet_service.exception.UserNotFound;
import com.petinder.pet_service.invoker.UserServiceInvoker;
import com.petinder.pet_service.mapper.ShelterMapper;
import com.petinder.pet_service.model.Shelter;
import com.petinder.pet_service.repository.ShelterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShelterServiceImpl implements ShelterService {
    private final ShelterMapper shelterMapper;
    private final ShelterRepository shelterRepository;
    private final UserServiceInvoker userServiceInvoker;

    public CreateShelterOutput createShelter(final CreateShelterInput input) {
        Shelter shelter = shelterMapper.createShelterInputToShelter(input);
        if (!userServiceInvoker.checkUserIds(input.getOwnerId())) {
            throw new UserNotFound(input.getOwnerId());
        }

        shelter = shelterRepository.save(shelter);
        return shelterMapper.shelterToCreateShelterOutput(shelter);
    }

    @Transactional
    public UpdateShelterOutput updateShelter(final UpdateShelterInput input) {
        final Shelter shelter = shelterRepository.findById(input.getId())
                .orElseThrow(() -> new ShelterNotFound(input.getId()));

        shelterMapper.updateShelterFromUpdateShelterInput(shelter, input);
        shelterRepository.save(shelter);

        return shelterMapper.shelterToUpdateShelterOutput(shelter);
    }

    public ReadShelterOutput readShelter(final ReadShelterInput input) {
        final Shelter shelter = shelterRepository.findById(input.getId())
                .orElseThrow(() -> new ShelterNotFound(input.getId()));
        return shelterMapper.shelterToReadShelterOutput(shelter);
    }

    public ListShelterOutput listShelter(final ListShelterInput input) {
        final Page<Shelter> shelters = shelterRepository.findAll(input.getPageable());

        final Pageable nextPageable = shelters.nextOrLastPageable();
        final int size = nextPageable.getPageSize();
        final int totalPage = shelters.getTotalPages();
        final int nextPage = nextPageable.getPageNumber();
        final long totalElements = shelters.getTotalElements();
        final List<ReadShelterOutput> readShelterOutputs = shelters.getContent()
                .stream()
                .map(shelterMapper::shelterToReadShelterOutput)
                .toList();

        return ListShelterOutput.builder()
                .shelters(readShelterOutputs)
                .size(size)
                .totalPage(totalPage)
                .nextPage(nextPage)
                .totalElements(totalElements)
                .build();
    }
}
