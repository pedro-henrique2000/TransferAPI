package com.project.transferapi.infra.mapper;

import com.project.transferapi.domain.entity.User;
import com.project.transferapi.domain.entity.UserType;
import com.project.transferapi.infra.model.UserModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserModelMapperTest {

    @InjectMocks
    UserModelMapper mapper;

    @Test
    void whenMapToModel_givenValidEntity_thenReturnModel() {
        User userEntity = createUserEntity();

        UserModel model = mapper.toModel(userEntity);

        assertNotNull(model.getCreatedAt());
        assertNotNull(model.getUpdatedAt());
        assertEquals(1L, model.getId());
        assertEquals("COMMON", model.getType());
        assertEquals("cnpj", model.getLegalDocumentNumber());
        assertEquals("email", model.getEmail());
        assertEquals("password", model.getPassword());
        assertEquals("name", model.getFullName());
        assertEquals(BigDecimal.TEN, model.getBalance());
    }

    @Test
    void whenMapToEntity_givenValidModel_thenReturnEntity() {
        UserModel model = createUserModel();

        User entity = mapper.toEntity(model);

        assertNotNull(entity.getCreatedAt());
        assertNotNull(entity.getUpdatedAt());
        assertEquals(1L, entity.getId());
        assertEquals(UserType.COMMON, entity.getType());
        assertEquals("cnpj", entity.getLegalDocumentNumber());
        assertEquals("email", entity.getEmail());
        assertEquals("password", entity.getPassword());
        assertEquals("name", entity.getFullName());
        assertEquals(BigDecimal.TEN, entity.getBalance());
    }

    private static UserModel createUserModel() {
        return UserModel.builder()
                .id(1L)
                .updatedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .type("COMMON")
                .legalDocumentNumber("cnpj")
                .email("email")
                .password("password")
                .fullName("name")
                .balance(BigDecimal.TEN)
                .build();
    }

    private static User createUserEntity() {
        return User.builder()
                .id(1L)
                .updatedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .type(UserType.COMMON)
                .legalDocumentNumber("cnpj")
                .email("email")
                .password("password")
                .fullName("name")
                .balance(BigDecimal.TEN)
                .build();
    }

}
