package com.project.transferapi.interfaces.inbound.http.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import static java.util.Objects.isNull;

@Getter
@Setter
public class CreateUserRequest {

    @NotBlank
    @Schema(description = "User Full Name", example = "John Doe")
    private String fullName;

    @NotBlank
    @Schema(description = "User Legal Document Number", example = "123456789")
    private String legalDocumentNumber;

    @NotBlank
    @Schema(description = "User Email", example = "mail@mail.com")
    private String email;

    @NotBlank
    @Schema(description = "User Password", example = "123456")
    private String password;

    @NotBlank
    @Schema(description = "Password Confirmation (same as password)", example = "123456")
    private String passwordConfirmation;

    @NotBlank
    @Schema(description = "User type (SHOPPER or COMMON)", example = "SHOPPER")
    private String type;

    @Schema(hidden = true)
    @JsonIgnore
    private boolean validPasswordConfirmation;

    @AssertTrue(message = "password and password confirmation are not equal")
    @Schema(hidden = true)
    public boolean isValidPasswordConfirmation() {
        if (isNull(this.password) || isNull(this.passwordConfirmation)) {
            return false;
        }
        return this.password.equals(this.passwordConfirmation);
    }

}
