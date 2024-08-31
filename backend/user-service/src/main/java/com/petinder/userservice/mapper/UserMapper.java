package com.petinder.userservice.mapper;

import com.petinder.userservice.dto.comm.CreateUserInput;
import com.petinder.userservice.dto.user.read.ReadUserOutput;
import com.petinder.userservice.dto.user.update.UpdateUserInput;
import com.petinder.userservice.dto.user.update.UpdateUserOutput;
import com.petinder.userservice.model.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User createUserInputToUser(CreateUserInput input);


    ReadUserOutput userToReadUserOutput(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateUserInputToUser(UpdateUserInput input, @MappingTarget User user);

    UpdateUserOutput userToUpdateUserOutput(User user);
}
