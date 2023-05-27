package com.project.transferapi.domain.ports;

public interface UserExistsByLegalDocumentNumberRepositoryPort {
   boolean existsByDocumentNumber(final String number);
}
