package com.petinder.userservice.service;

import com.petinder.userservice.dto.create.CreateUserInput;
import com.petinder.userservice.dto.create.CreateUserOutput;
import com.petinder.userservice.dto.delete.DeleteUserInput;
import com.petinder.userservice.dto.delete.DeleteUserOutput;
import com.petinder.userservice.dto.list.ListUserInput;
import com.petinder.userservice.dto.list.ListUserOutput;
import com.petinder.userservice.dto.read.ReadUserInput;
import com.petinder.userservice.dto.read.ReadUserOutput;
import com.petinder.userservice.dto.update.UpdateUserInput;
import com.petinder.userservice.dto.update.UpdateUserOutput;

public interface UserService {
    CreateUserOutput createUser(CreateUserInput input);

    ReadUserOutput readUser(ReadUserInput input);

    UpdateUserOutput updateUser(UpdateUserInput input);

    DeleteUserOutput deleteUser(DeleteUserInput input);

    ListUserOutput listUser(ListUserInput input);
}
