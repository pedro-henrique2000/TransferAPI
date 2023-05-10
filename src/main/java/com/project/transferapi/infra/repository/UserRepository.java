package com.project.transferapi.infra.repository;

import com.project.transferapi.domain.entity.User;
import com.project.transferapi.domain.ports.IFindUserByEmail;
import com.project.transferapi.domain.ports.IFindUserById;
import com.project.transferapi.domain.ports.IFindUserByLegalDocumentNumber;
import com.project.transferapi.domain.ports.ISaveUserRepository;
import com.project.transferapi.infra.mapper.UserModelMapper;
import com.project.transferapi.infra.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepository implements IFindUserByEmail, IFindUserByLegalDocumentNumber, ISaveUserRepository, IFindUserById {

    private final JpaUserRepository jpaUserRepository;
    private final UserModelMapper userModelMapper;

    @Override
    public Optional<User> findByEmail(final String email) {
        Optional<UserModel> userModel = this.jpaUserRepository.findByEmail(email);
        return this.toUserEntity(userModel);
    }

    @Override
    public Optional<User> findByLegalDocumentNumber(final String legalDocumentNumber) {
        Optional<UserModel> userModel = this.jpaUserRepository.findByLegalDocumentNumber(legalDocumentNumber);
        return this.toUserEntity(userModel);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        Optional<UserModel> userModel = this.jpaUserRepository.findById(id);
        return this.toUserEntity(userModel);
    }

    @Override
    public User save(final User user) {
        UserModel savedUserModel = this.jpaUserRepository.save(this.userModelMapper.toModel(user));
        return this.userModelMapper.toEntity(savedUserModel);
    }

    private Optional<User> toUserEntity(final Optional<UserModel> userModel) {
        return userModel.map(this.userModelMapper::toEntity);
    }
}
