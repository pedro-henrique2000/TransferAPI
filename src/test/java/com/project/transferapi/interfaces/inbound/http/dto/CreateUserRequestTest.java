package com.project.transferapi.interfaces.inbound.http.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateUserRequestTest {

    @Test
    void shouldReturnFalseWhenPasswordIsNull() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        boolean validPasswordCombination = createUserRequest.isValidPasswordConfirmation();
        assertFalse(validPasswordCombination);
    }

}
