package com.petinder.userservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExceptionDTO {
    private String code;
    private List<String> message;
}
