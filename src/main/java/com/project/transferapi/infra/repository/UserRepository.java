package com.project.transferapi.infra.repository;

import com.project.transferapi.domain.entity.User;
import com.project.transferapi.domain.ports.IFindUserByEmail;
import com.project.transferapi.domain.ports.IFindUserByLegalDocumentNumber;
import com.project.transferapi.infra.mapper.UserModelMapper;
import com.project.transferapi.infra.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepository implements IFindUserByEmail, IFindUserByLegalDocumentNumber {

    private final JpaUserRepository jpaUserRepository;
    private final UserModelMapper userModelMapper;

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<UserModel> userModel = this.jpaUserRepository.findByEmail(email);
        return this.toUserEntity(userModel);
    }

    @Override
    public Optional<User> findByLegalDocumentNumber(String legalDocumentNumber) {
        Optional<UserModel> userModel = this.jpaUserRepository.findByLegalDocumentNumber(legalDocumentNumber);
        return this.toUserEntity(userModel);
    }

    private Optional<User> toUserEntity(Optional<UserModel> userModel) {
        return userModel.map(this.userModelMapper::toEntity);
    }
}
