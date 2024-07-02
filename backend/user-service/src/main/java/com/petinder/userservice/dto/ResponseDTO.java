package com.petinder.userservice.dto;

public record ResponseDTO<T>(T data, ExceptionDTO error) {
    public static <T> ResponseDTO<T> success(T data) {
        return new ResponseDTO<>(data, null);
    }

    public static <T> ResponseDTO<T> error(ExceptionDTO error) {
        return new ResponseDTO<>(null, error);
    }
}
