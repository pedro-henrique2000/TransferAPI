package com.project.transferapi.domain.ports;

import com.project.transferapi.domain.entity.User;

import java.math.BigDecimal;

public interface ITransferNotification {
    void invoke(User source, User destination, BigDecimal balance);
}
