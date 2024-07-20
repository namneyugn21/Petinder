package com.petinder.pet_service.mapper;

import com.petinder.pet_service.dto.create.CreatePetInput;
import com.petinder.pet_service.dto.create.CreatePetOutput;
import com.petinder.pet_service.dto.list.ListPetOutput;
import com.petinder.pet_service.dto.read.ReadPetOutput;
import com.petinder.pet_service.dto.update.UpdatePetInput;
import com.petinder.pet_service.dto.update.UpdatePetOutput;
import com.petinder.pet_service.model.Pet;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PetMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @Mapping(target = "property.age", source = "age")
    @Mapping(target = "property.weight", source = "weight")
    @Mapping(target = "property.breed", source = "breed")
    @Mapping(target = "property.furColor", source = "furColor")
    @Mapping(target = "property.eyeColor", source = "eyeColor")
    Pet createPetInputToPet(CreatePetInput createPetInput);

    @Mapping(target = "age", source = "property.age")
    @Mapping(target = "weight", source = "property.weight")
    @Mapping(target = "breed", source = "property.breed")
    @Mapping(target = "furColor", source = "property.furColor")
    @Mapping(target = "eyeColor", source = "property.eyeColor")
    CreatePetOutput petToCreatePetOutput(Pet pet);

    @Mapping(target = "age", source = "property.age")
    @Mapping(target = "weight", source = "property.weight")
    @Mapping(target = "breed", source = "property.breed")
    @Mapping(target = "furColor", source = "property.furColor")
    @Mapping(target = "eyeColor", source = "property.eyeColor")
    ReadPetOutput petToReadPetOutput(Pet pet);

    @Mapping(target = "createAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    @Mapping(target = "property.age", source = "age")
    @Mapping(target = "property.weight", source = "weight")
    @Mapping(target = "property.breed", source = "breed")
    @Mapping(target = "property.furColor", source = "furColor")
    @Mapping(target = "property.eyeColor", source = "eyeColor")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePetInputToPet(UpdatePetInput updatePetInput, @MappingTarget Pet pet);

    @Mapping(target = "age", source = "property.age")
    @Mapping(target = "weight", source = "property.weight")
    @Mapping(target = "breed", source = "property.breed")
    @Mapping(target = "furColor", source = "property.furColor")
    @Mapping(target = "eyeColor", source = "property.eyeColor")
    UpdatePetOutput petToUpdatePetOutput(Pet pet);

    @Mapping(target = "pets", source = "readPetOutputs")
    @Mapping(target = "nextPage", source = "nextPage")
    @Mapping(target = "nextSize", source = "nextSize")
    @Mapping(target = "totalPage", source = "totalPage")
    ListPetOutput toListPetOutput(List<ReadPetOutput> readPetOutputs, int nextPage, int nextSize, int totalPage);
}
