package com.project.transferapi.domain.entity;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class User {

    private Long id;
    private String fullName;
    private String legalDocumentNumber;
    private String email;
    private String password;
    private BigDecimal balance;
    private UserType type;

}
