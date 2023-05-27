package com.project.transferapi.domain.ports;

public interface PasswordComparerPort {
    boolean equals(String password, String encrypted);
}
