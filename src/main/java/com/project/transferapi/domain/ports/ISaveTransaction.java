package com.project.transferapi.domain.ports;

import com.project.transferapi.domain.entity.Transaction;

public interface ISaveTransaction {
    Transaction save(Transaction transaction);
}
