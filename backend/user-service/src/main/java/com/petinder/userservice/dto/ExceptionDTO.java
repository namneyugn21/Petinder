package com.petinder.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ExceptionDTO {
    private String code;
    private List<String> message;

    public ExceptionDTO(final String code, final String message) {
        this.code = code;
        this.message = new ArrayList<>() {
            {
                add(message);
            }
        };
    }
}
