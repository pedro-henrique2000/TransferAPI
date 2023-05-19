package com.project.transferapi.domain.ports;

public interface IUserExistsByLegalDocumentNumberRepository {
    boolean existsByDocumentNumber(final String number);
}
