package com.project.transferapi.interfaces.outbound.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class TransferNotificationDTO {
    private String sourceName;
    private String destinationName;
    private BigDecimal amount;
    private String status;
}
