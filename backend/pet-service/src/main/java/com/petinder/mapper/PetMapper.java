package com.petinder.mapper;

import com.petinder.dto.create.CreatePetInput;
import com.petinder.dto.create.CreatePetOutput;
import com.petinder.dto.list.ListPetOutput;
import com.petinder.dto.read.ReadPetOutput;
import com.petinder.dto.update.UpdatePetInput;
import com.petinder.dto.update.UpdatePetOutput;
import com.petinder.model.Pet;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PetMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "createPetInput.name")
    @Mapping(target = "picture", source = "createPetInput.picture")
    @Mapping(target = "property.age", source = "createPetInput.age")
    @Mapping(target = "property.weight", source = "createPetInput.weight")
    @Mapping(target = "property.breed", source = "createPetInput.breed")
    @Mapping(target = "property.furColor", source = "createPetInput.furColor")
    @Mapping(target = "property.eyeColor", source = "createPetInput.eyeColor")
    Pet createPetInputToPet(CreatePetInput createPetInput);

    @Mapping(target = "id", source = "pet.id")
    @Mapping(target = "name", source = "pet.name")
    @Mapping(target = "picture", source = "pet.picture")
    @Mapping(target = "age", source = "pet.property.age")
    @Mapping(target = "weight", source = "pet.property.weight")
    @Mapping(target = "breed", source = "pet.property.breed")
    @Mapping(target = "furColor", source = "pet.property.furColor")
    @Mapping(target = "eyeColor", source = "pet.property.eyeColor")
    CreatePetOutput petToCreatePetOutput(Pet pet);

    @Mapping(target = "id", source = "pet.id")
    @Mapping(target = "name", source = "pet.name")
    @Mapping(target = "picture", source = "pet.picture")
    @Mapping(target = "age", source = "pet.property.age")
    @Mapping(target = "weight", source = "pet.property.weight")
    @Mapping(target = "breed", source = "pet.property.breed")
    @Mapping(target = "furColor", source = "pet.property.furColor")
    @Mapping(target = "eyeColor", source = "pet.property.eyeColor")
    ReadPetOutput petToReadPetOutput(Pet pet);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "picture", source = "picture")
    @Mapping(target = "pet.property.age", source = "age")
    @Mapping(target = "pet.property.weight", source = "weight")
    @Mapping(target = "pet.property.breed", source = "breed")
    @Mapping(target = "pet.property.furColor", source = "furColor")
    @Mapping(target = "pet.property.eyeColor", source = "eyeColor")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePetInputToPet(UpdatePetInput updatePetInput, @MappingTarget Pet pet);

    @Mapping(target = "id", source = "pet.id")
    @Mapping(target = "name", source = "pet.name")
    @Mapping(target = "picture", source = "pet.picture")
    @Mapping(target = "age", source = "pet.property.age")
    @Mapping(target = "weight", source = "pet.property.weight")
    @Mapping(target = "breed", source = "pet.property.breed")
    @Mapping(target = "furColor", source = "pet.property.furColor")
    @Mapping(target = "eyeColor", source = "pet.property.eyeColor")
    UpdatePetOutput petToUpdatePetOutput(Pet pet);

    @Mapping(target = "pets", source = "readPetOutputs")
    @Mapping(target = "nextPage", source = "nextPage")
    @Mapping(target = "size", source = "size")
    @Mapping(target = "totalPage", source = "totalPage")
    ListPetOutput toListPetOutput(List<ReadPetOutput> readPetOutputs, int nextPage, int size, int totalPage);
}
