package com.petinder.userservice.dto.user.list;

import com.petinder.userservice.dto.user.read.ReadUserOutput;
import lombok.Data;

import java.util.List;

@Data
public class ListUserOutput {
    private List<ReadUserOutput> users;
    private int nextPage;
    private int size;
    private int totalPage;
}
