package com.project.transferapi.interfaces.inbound.http.mapper;

import com.project.transferapi.domain.entity.Role;
import com.project.transferapi.domain.entity.User;
import com.project.transferapi.domain.entity.UserType;
import com.project.transferapi.interfaces.inbound.http.dto.CreateUserRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class UserDTOMapper {

    public User toUserEntity(CreateUserRequest createUserRequest) {
        return User.builder()
                .balance(BigDecimal.ZERO)
                .fullName(createUserRequest.getFullName())
                .type(UserType.valueOf(createUserRequest.getType()))
                .email(createUserRequest.getEmail())
                .password(createUserRequest.getPassword())
                .role(Role.USER)
                .legalDocumentNumber(createUserRequest.getLegalDocumentNumber())
                .build();
    }

}
