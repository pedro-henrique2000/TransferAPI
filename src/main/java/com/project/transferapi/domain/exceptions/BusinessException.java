package com.project.transferapi.domain.exceptions;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
