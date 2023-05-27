package com.project.transferapi.interfaces.inbound.http.controller;

import com.project.transferapi.domain.entity.User;
import com.project.transferapi.domain.entity.UserType;
import com.project.transferapi.interfaces.inbound.http.dto.CreateUserRequest;

import java.math.BigDecimal;

public class UserFixture {

    public static CreateUserRequest getValidCreateUserRequest() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setLegalDocumentNumber("cnpj");
        createUserRequest.setType("COMMON");
        createUserRequest.setEmail("mail@mail.com");
        createUserRequest.setPassword("password");
        createUserRequest.setPasswordConfirmation("password");
        createUserRequest.setFullName("name");
        return createUserRequest;
    }

    public static CreateUserRequest getCreateUserDTOWithInvalidPassword() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setLegalDocumentNumber("cnpj");
        createUserRequest.setType("COMMON");
        createUserRequest.setEmail("mail@mail.com");
        createUserRequest.setPassword("password1");
        createUserRequest.setPasswordConfirmation("password2");
        createUserRequest.setFullName("name");
        return createUserRequest;
    }

    public static User getUserModelWithDuplicatedEmail() {
        return User.builder()
                .password("123")
                .type(UserType.COMMON)
                .email("mail@mail.com")
                .legalDocumentNumber("cnpj2")
                .balance(BigDecimal.ZERO)
                .fullName("NAME")
                .build();
    }

    public static User getUserModelWithDuplicatedDocument() {
        return User.builder()
                .password("123")
                .type(UserType.COMMON)
                .email("mail@mail.com")
                .legalDocumentNumber("cnpj")
                .balance(BigDecimal.ZERO)
                .fullName("NAME")
                .build();
    }

}
