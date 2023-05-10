package com.project.transferapi.application;

import com.project.transferapi.domain.ports.IFindUserById;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransferAmount {

    private final IFindUserById findUserById;

    public void invoke(Long sourceId, Long destinationId, BigDecimal amount) {

    }

}