package com.project.transferapi.interfaces.inbound.http.handler;

import com.project.transferapi.domain.exceptions.ConflictException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

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

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException methodArgumentNotValidException, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<FieldError> fieldErrors = methodArgumentNotValidException.getBindingResult().getFieldErrors();
        Map<String, String> violations = this.getViolations(fieldErrors);
        ValidationExceptionDetails validationExceptionDetails = ValidationExceptionDetails.builder()
                .details("Check the wrong fields")
                .timestamp(LocalDateTime.now())
                .title("Field Validation Exception")
                .developerMessage(methodArgumentNotValidException.getClass().getName())
                .status(HttpStatus.BAD_REQUEST.value())
                .violations(violations)
                .build();
        return new ResponseEntity<>(validationExceptionDetails, HttpStatus.BAD_REQUEST);
    }

    private Map<String, String> getViolations(List<FieldError> fieldErrors) {
        Map<String, String> violations = new HashMap<>();
        for (FieldError fieldError : fieldErrors) {
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            violations.put(field, message);
        }
        return violations;
    }

}