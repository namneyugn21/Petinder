package com.petinder.pet_service.exception.handler;

import com.petinder.pet_service.dto.ResponseDTO;
import com.petinder.pet_service.exception.PetNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.Set;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String GENERIC_MSG =
            "Something went wrong. Please try again, and contact us if the problem persists.";

    private static final Map<Class<? extends RuntimeException>, HttpStatus> EXCEPTION_TO_HTTP_STATUS = Map.of(
            PetNotFound.class, HttpStatus.NOT_FOUND
    );

    private static final Set<Class<? extends RuntimeException>> WHITE_LIST = Set.of(
            PetNotFound.class
    );

    @ExceptionHandler
    ResponseEntity<ResponseDTO<?>> exceptionHandler(RuntimeException e) {
        log.error(String.valueOf(e));

        String message = WHITE_LIST.contains(e.getClass()) ? e.getMessage() : GENERIC_MSG;
        HttpStatus status = EXCEPTION_TO_HTTP_STATUS.getOrDefault(e.getClass(), HttpStatus.INTERNAL_SERVER_ERROR);

        ResponseDTO<?> body = ResponseDTO.error(new ExceptionDTO(message));
        return ResponseEntity
                .status(status)
                .body(body);
    }
}
