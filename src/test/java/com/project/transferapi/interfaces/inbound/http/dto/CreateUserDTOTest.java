package com.project.transferapi.interfaces.inbound.http.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateUserDTOTest {

    @Test
    void shouldReturnFalseWhenPasswordIsNull() {
        CreateUserDTO createUserDTO = new CreateUserDTO();
        boolean validPasswordCombination = createUserDTO.isValidPasswordCombination();
        assertFalse(validPasswordCombination);
    }

}
