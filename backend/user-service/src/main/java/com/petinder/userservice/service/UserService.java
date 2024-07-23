package com.petinder.userservice.service;

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

public interface UserService {
    CreateUserOutput createUser(CreateUserInput input);

    ReadUserOutput readUser(ReadUserInput input);

    UpdateUserOutput updateUser(UpdateUserInput input);

    DeleteUserOutput deleteUser(DeleteUserInput input);

    ListUserOutput listUser(ListUserInput input);
}
