package com.project.transferapi.interfaces.inbound.http.controller;

import com.project.transferapi.application.TransferAmount;
import com.project.transferapi.interfaces.inbound.http.dto.TransferAmountRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/transfer")
@Tag(name = "Transfer Management", description = "Transfer Management Endpoints")
public class TransferController {

   private final TransferAmount transferAmount;

   @PreAuthorize("#transferAmountRequest.sourceId == authentication.principal.id")
   @PostMapping()
   @ResponseStatus(HttpStatus.CREATED)
   @Operation(
         description = "Execute an Transaction",
         summary = "Transaction Endpoint",
         responses = {
               @ApiResponse(
                     description = "Success Response",
                     responseCode = "201"
               )
         })
   public ResponseEntity<Void> transfer(@RequestBody @Valid TransferAmountRequest transferAmountRequest) {
      this.transferAmount.invoke(
            transferAmountRequest.getSourceId(),
            transferAmountRequest.getDestinationId(),
            transferAmountRequest.getAmount()
      );

      return ResponseEntity
            .status(201)
            .build();
   }

}
