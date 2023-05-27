package com.project.transferapi.interfaces.inbound.http.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationResponse {
    @Schema(required = true, example = "ey27887178e8172312412", description = "Generated User Token")
    private String accessToken;
}
