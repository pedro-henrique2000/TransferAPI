package com.project.transferapi.interfaces.inbound.http.controller;

import com.project.transferapi.application.TransferAmount;
import com.project.transferapi.interfaces.inbound.http.dto.TransferAmountDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/transfer")
public class TransferController {

    private final TransferAmount transferAmount;

    @PreAuthorize("#transferAmountDTO.sourceId == authentication.principal.id")
    @PostMapping
    public ResponseEntity<Void> transfer(@RequestBody @Valid TransferAmountDTO transferAmountDTO) {
        this.transferAmount.invoke(
                transferAmountDTO.getSourceId(),
                transferAmountDTO.getDestinationId(),
                transferAmountDTO.getAmount()
        );

        return ResponseEntity
                .status(201)
                .build();
    }

}
