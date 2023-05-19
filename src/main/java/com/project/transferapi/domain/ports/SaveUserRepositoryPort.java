package com.project.transferapi.domain.ports;

import com.project.transferapi.domain.entity.User;

public interface SaveUserRepositoryPort {
    User save(User user);
}
