package com.project.transferapi.interfaces.inbound.http.controller;

import com.project.transferapi.infra.model.UserModel;
import com.project.transferapi.interfaces.inbound.http.dto.CreateUserDTO;

import java.math.BigDecimal;

public class UserFixture {

    public static CreateUserDTO getValidCreateUserRequest() {
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setLegalDocumentNumber("cnpj");
        createUserDTO.setType("COMMON");
        createUserDTO.setEmail("mail@mail.com");
        createUserDTO.setPassword("password");
        createUserDTO.setPasswordConfirmation("password");
        createUserDTO.setFullName("name");
        return createUserDTO;
    }

    public static CreateUserDTO getCreateUserDTOWithInvalidPassword() {
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setLegalDocumentNumber("cnpj");
        createUserDTO.setType("COMMON");
        createUserDTO.setEmail("mail@mail.com");
        createUserDTO.setPassword("password1");
        createUserDTO.setPasswordConfirmation("password2");
        createUserDTO.setFullName("name");
        return createUserDTO;
    }

    public static UserModel getUserModelWithDuplicatedEmail() {
        return UserModel.builder()
                .password("123")
                .type("COMMON")
                .email("mail@mail.com")
                .legalDocumentNumber("cnpj2")
                .balance(BigDecimal.ZERO)
                .fullName("NAME")
                .build();
    }

    public static UserModel getUserModelWithDuplicatedDocument() {
        return UserModel.builder()
                .password("123")
                .type("COMMON")
                .email("mail@mail.com")
                .legalDocumentNumber("cnpj")
                .balance(BigDecimal.ZERO)
                .fullName("NAME")
                .build();
    }

}
