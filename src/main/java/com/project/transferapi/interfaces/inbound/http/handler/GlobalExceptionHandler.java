package com.project.transferapi.interfaces.inbound.http.handler;

import com.project.transferapi.domain.exceptions.ConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ExceptionDetails> handleConflictException(final ConflictException conflictException) {
        final ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                .status(HttpStatus.CONFLICT.value())
                .details(conflictException.getMessage())
                .title("Conflict Exception")
                .timestamp(LocalDateTime.now())
                .developerMessage(conflictException.getClass().getName())
                .build();

        return ResponseEntity.status(409).body(exceptionDetails);
    }

}
