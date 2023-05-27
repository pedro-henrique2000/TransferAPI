package com.project.transferapi.domain.ports;

public interface ManagerAuthenticationPort {
    void authentication(String email, String password);
}
