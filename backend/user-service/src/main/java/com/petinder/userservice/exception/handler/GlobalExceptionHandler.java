package com.petinder.userservice.exception.handler;

import com.petinder.userservice.dto.ExceptionDTO;
import com.petinder.userservice.dto.ResponseDTO;
import com.petinder.userservice.exception.BaseException;
import com.petinder.userservice.exception.UserNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String UNKNOWN_CODE = "UNKNOWN";
    private static final String GENERIC_MSG =
            "Something went wrong. Please try again, and contact us if the problem persists.";

    private static final Map<Class<? extends Exception>, String> EXCEPTION_TO_CODE = Map.of(
            UserNotFound.class, "USR_1",
            WebExchangeBindException.class, "USR_2"
    );

    private static final Map<Class<? extends Exception>, HttpStatus> EXCEPTION_TO_HTTP_STATUS = Map.of(
            UserNotFound.class, HttpStatus.NOT_FOUND,
            WebExchangeBindException.class, HttpStatus.BAD_REQUEST
    );

    private static final Set<Class<? extends Exception>> WHITE_LIST = Set.of(
            UserNotFound.class,
            WebExchangeBindException.class
    );

    @Override
    protected Mono<ResponseEntity<Object>> handleWebExchangeBindException(
            WebExchangeBindException e,
            HttpHeaders headers,
            HttpStatusCode status,
            ServerWebExchange exchange
    ) {
        List<String> errorList = e
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        String code = EXCEPTION_TO_CODE.getOrDefault(e.getClass(), UNKNOWN_CODE);
        ExceptionDTO exceptionDTO = ExceptionDTO.builder()
                .code(code)
                .message(errorList)
                .build();
        ResponseDTO<ExceptionDTO> body = ResponseDTO.error(exceptionDTO);

        return handleExceptionInternal(e, body, headers, status, exchange);
    }


    @ExceptionHandler
    public ResponseEntity<ResponseDTO<ExceptionDTO>> exceptionHandler(
            BaseException e
    ) {
        log.error(String.valueOf(e));

        // Body
        String code = EXCEPTION_TO_CODE.getOrDefault(e.getClass(), UNKNOWN_CODE);
        List<String> message = List.of(WHITE_LIST.contains(e.getClass()) ? e.getMessage() : GENERIC_MSG);
        ExceptionDTO exceptionDTO = ExceptionDTO.builder()
                .code(code)
                .message(message)
                .build();
        ResponseDTO<ExceptionDTO> body = ResponseDTO.error(exceptionDTO);

        // HTTP Status
        HttpStatus status = EXCEPTION_TO_HTTP_STATUS.getOrDefault(
                e.getClass(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );

        return ResponseEntity
                .status(status)
                .body(body);
    }
}
