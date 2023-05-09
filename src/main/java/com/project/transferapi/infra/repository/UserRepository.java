package com.project.transferapi.infra.repository;

import com.project.transferapi.domain.entity.User;
import com.project.transferapi.domain.ports.IFindUserByEmail;
import com.project.transferapi.infra.mapper.UserModelMapper;
import com.project.transferapi.infra.model.UserModel;
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
        Optional<UserModel> userModel = this.userRepository.findByEmail(email);
        return userModel.map(this.userModelMapper::toEntity);
    }

}
