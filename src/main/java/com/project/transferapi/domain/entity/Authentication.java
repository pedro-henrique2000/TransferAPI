package com.project.transferapi.domain.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Authentication {

    private String accessToken;
    private String refreshToken;

}
