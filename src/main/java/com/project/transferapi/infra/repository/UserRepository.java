package com.project.transferapi.infra.repository;

import com.project.transferapi.domain.entity.User;
import com.project.transferapi.domain.ports.IFindUserByEmail;
import com.project.transferapi.infra.mapper.UserModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepository implements IFindUserByEmail {

    private final JpaUserRepository userRepository;
    private final UserModelMapper userModelMapper;

    @Override
    public Optional<User> find(String email) {
        this.userRepository.findByEmail(email);
        return Optional.empty();
    }

}
