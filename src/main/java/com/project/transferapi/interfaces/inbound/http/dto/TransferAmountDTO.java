package com.project.transferapi.interfaces.inbound.http.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Setter
@Getter
public class TransferAmountDTO {

    @NotNull
    private Long sourceId;

    @NotNull
    private Long destinationId;

    @NotNull
    @Positive
    private BigDecimal amount;

    @AssertTrue(message = "source and destination cannot be the same")
    public boolean isSourceAndDestinationNotEqual() {
        return !Objects.equals(sourceId, destinationId);
    }

}
