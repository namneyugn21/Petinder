package com.petinder.userservice.dto.user.list;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
@Builder
public class ListUserInput {
    private Pageable pageable;
}
