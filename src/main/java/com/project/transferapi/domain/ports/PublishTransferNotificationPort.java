package com.project.transferapi.domain.ports;

import com.project.transferapi.domain.entity.Transaction;

public interface PublishTransferNotificationPort {
   void notify(Transaction transaction);
}
