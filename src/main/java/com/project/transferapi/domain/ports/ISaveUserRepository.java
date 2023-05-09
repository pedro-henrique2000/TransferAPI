package com.project.transferapi.domain.ports;

import com.project.transferapi.domain.entity.User;

public interface ISaveUserRepository {
    User save(User user);
}
