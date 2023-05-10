package com.project.transferapi.interfaces.inbound.http.mapper;

import com.project.transferapi.domain.entity.User;
import com.project.transferapi.domain.entity.UserType;
import com.project.transferapi.interfaces.inbound.http.dto.CreateUserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserDTOMapperTest {

    @InjectMocks
    UserDTOMapper mapper;

    @Test
    void whenMapToUser_givenValidDTO_thenConvertCorrectly() {
        CreateUserDTO userDTO = getUserDTO();
        User userEntity = this.mapper.toUserEntity(userDTO);

        assertEquals("mail", userEntity.getEmail());
        assertEquals(UserType.COMMON, userEntity.getType());
        assertEquals("pass", userEntity.getPassword());
        assertEquals("name", userEntity.getFullName());
        assertEquals("cnpj", userEntity.getLegalDocumentNumber());
    }

    private static CreateUserDTO getUserDTO() {
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setEmail("mail");
        createUserDTO.setType("COMMON");
        createUserDTO.setPassword("pass");
        createUserDTO.setFullName("name");
        createUserDTO.setLegalDocumentNumber("cnpj");
        return createUserDTO;
    }

}
