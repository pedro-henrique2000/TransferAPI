package com.project.transferapi.interfaces.inbound.http.mapper;

import com.project.transferapi.domain.entity.User;
import com.project.transferapi.domain.entity.UserType;
import com.project.transferapi.interfaces.inbound.http.dto.CreateUserDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class UserDTOMapper {

    public User toUserEntity(CreateUserDTO createUserDTO) {
        return User.builder()
                .balance(BigDecimal.ZERO)
                .fullName(createUserDTO.getFullName())
                .type(UserType.valueOf(createUserDTO.getType()))
                .email(createUserDTO.getEmail())
                .password(createUserDTO.getPassword())
                .legalDocumentNumber(createUserDTO.getLegalDocumentNumber())
                .build();
    }

}
