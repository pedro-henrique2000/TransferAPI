package com.project.transferapi.infra.mapper;

import com.project.transferapi.domain.entity.User;
import com.project.transferapi.domain.entity.UserType;
import com.project.transferapi.infra.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserModelMapper {

    public User toEntity(UserModel userModel) {
        return User.builder()
                .id(userModel.getId())
                .type(UserType.valueOf(userModel.getType()))
                .legalDocumentNumber(userModel.getLegalDocumentNumber())
                .email(userModel.getEmail())
                .password(userModel.getPassword())
                .balance(userModel.getBalance())
                .fullName(userModel.getFullName())
                .build();
    }

}
