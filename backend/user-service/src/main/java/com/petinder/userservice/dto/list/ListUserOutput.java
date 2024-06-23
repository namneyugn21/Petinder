package com.petinder.userservice.dto.list;

import com.petinder.userservice.dto.read.ReadUserOutput;
import lombok.Data;

import java.util.List;

@Data
public class ListUserOutput {
    private List<ReadUserOutput> users;
    private int nextPage;
    private int size;
    private int totalPage;
}
