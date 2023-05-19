package com.project.transferapi.interfaces.inbound.http.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import static java.util.Objects.isNull;

@Getter
@Setter
public class CreateUserDTO {

    @NotBlank
    private String fullName;

    @NotBlank
    private String legalDocumentNumber;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String passwordConfirmation;

    @NotBlank
    private String type;

    @AssertTrue(message = "password and passoword confirmation are not equal")
    public boolean validatePasswordCombination() {
        if (isNull(this.password) || isNull(this.passwordConfirmation)) {
            return false;
        }
        return this.password.equals(this.passwordConfirmation);
    }

}
