package com.project.transferapi.interfaces.inbound.http.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDTO {

    private String fullName;
    private String legalDocumentNumber;
    private String email;
    private String password;
    private String passwordConfirmation;
    private String type;

}
