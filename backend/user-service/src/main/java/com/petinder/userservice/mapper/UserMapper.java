package com.petinder.userservice.mapper;

import com.petinder.userservice.dto.user.create.CreateUserInput;
import com.petinder.userservice.dto.user.create.CreateUserOutput;
import com.petinder.userservice.dto.user.list.ListUserOutput;
import com.petinder.userservice.dto.user.read.ReadUserOutput;
import com.petinder.userservice.dto.user.update.UpdateUserInput;
import com.petinder.userservice.dto.user.update.UpdateUserOutput;
import com.petinder.userservice.model.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User createUserInputToUser(CreateUserInput input);

    @Mapping(source = "id", target = "userId")
    CreateUserOutput userToCreateUserOutput(User user);

    ReadUserOutput userToReadUserOutput(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateUserInputToUser(UpdateUserInput input, @MappingTarget User user);

    UpdateUserOutput userToUpdateUserOutput(User user);

    ListUserOutput toListUserOutput(List<ReadUserOutput> users, int nextPage, int size, int totalPage);
}
