package com.petinder.userservice.dto.user.list;

import com.petinder.userservice.dto.user.read.ReadUserOutput;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ListUserOutput {
    private List<ReadUserOutput> users;
    private Integer nextPage;
    private Integer size;
    private Integer totalPage;
    private Long totalElements;
}
