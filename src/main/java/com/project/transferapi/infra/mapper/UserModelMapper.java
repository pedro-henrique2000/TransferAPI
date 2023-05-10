package com.project.transferapi.infra.mapper;

import com.project.transferapi.domain.entity.User;
import com.project.transferapi.domain.entity.UserType;
import com.project.transferapi.infra.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserModelMapper {

    public User toEntity(final UserModel userModel) {
        return User.builder()
                .id(userModel.getId())
                .type(UserType.valueOf(userModel.getType()))
                .legalDocumentNumber(userModel.getLegalDocumentNumber())
                .email(userModel.getEmail())
                .password(userModel.getPassword())
                .balance(userModel.getBalance())
                .fullName(userModel.getFullName())
                .createdAt(userModel.getCreatedAt())
                .updatedAt(userModel.getUpdatedAt())
                .build();
    }

    public UserModel toModel(final User user) {
        return UserModel.builder()
                .id(user.getId())
                .balance(user.getBalance())
                .password(user.getPassword())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .legalDocumentNumber(user.getLegalDocumentNumber())
                .type(user.getType().toString())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

}
