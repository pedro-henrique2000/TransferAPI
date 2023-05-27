package com.project.transferapi.interfaces.inbound.http.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Setter
@Getter
public class TransferAmountRequest {

   @NotNull
   @Schema(description = "User that will send the transfer amount", example = "1")
   private Long sourceId;

   @NotNull
   @Schema(description = "User that will receive the transfer amount", example = "2")
   private Long destinationId;

   @NotNull
   @Positive
   @Schema(description = "Amount to be transferer", example = "10.00")
   private BigDecimal amount;

   @AssertTrue(message = "source and destination cannot be the same")
   @JsonIgnore
   @Schema(hidden = true)
   public boolean isSourceAndDestinationNotEqual() {
      return !Objects.equals(sourceId, destinationId);
   }

}
