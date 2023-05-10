package com.project.transferapi.interfaces.inbound.http.handler;

import com.project.transferapi.domain.exceptions.ConflictException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    GlobalExceptionHandler handler;

    @Test
    void whenConflictExceptionOccurs_shouldReturn409() {
        ResponseEntity<ExceptionDetails> response = handler.handleConflictException(new ConflictException("msg"));
        assertEquals(409, response.getStatusCode().value());
    }

}