package com.petinder.userservice.exception.handler;


import com.petinder.userservice.dto.ExceptionDTO;
import com.petinder.userservice.dto.ResponseDTO;
import com.petinder.userservice.exception.BaseException;
import com.petinder.userservice.exception.black.BlackException;
import com.petinder.userservice.exception.white.WhiteException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({Exception.class, BlackException.class})
    ResponseEntity<ResponseDTO<ExceptionDTO>> unexpectExceptionHandler(
            final Exception e
    ) {
        final HttpStatus status;
        final ExceptionDTO exceptionDTO;

        // System/Known exception
        if (e instanceof BlackException blackException) {
            exceptionDTO = new ExceptionDTO(blackException.getCode(), blackException.getMessages());
            if (blackException.getStatus().is5xxServerError()) {
                log.error(e.toString());
                e.printStackTrace(System.err);
            } else {
                log.warn(e.toString());
            }
            status = blackException.getStatus();
        }

        // Unknown Exception
        else {
            exceptionDTO = new ExceptionDTO(BaseException.createCode(), BaseException.DEFAULT_MSG);
            log.error(String.valueOf(e));
            e.printStackTrace(System.err);
            status = BlackException.DEFAULT_STATUS;
        }

        final ResponseDTO<ExceptionDTO> body = ResponseDTO.error(exceptionDTO);
        return ResponseEntity
                .status(status)
                .body(body);
    }

    // Handle validation error
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        final List<String> errorList = e
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        final ResponseDTO<ExceptionDTO> body = ResponseDTO.error(new ExceptionDTO("USER_BR", errorList));
        return this.handleExceptionInternal(e, body, headers, status, request);
    }

    @ExceptionHandler
    ResponseEntity<ResponseDTO<ExceptionDTO>> expectExceptionHandler(
            WhiteException e
    ) {
        log.info(e.toString());

        final ExceptionDTO exception = new ExceptionDTO(e.getCode(), e.getMessages());
        final ResponseDTO<ExceptionDTO> body = ResponseDTO.error(exception);
        return ResponseEntity
                .status(e.getStatus())
                .body(body);
    }
}
