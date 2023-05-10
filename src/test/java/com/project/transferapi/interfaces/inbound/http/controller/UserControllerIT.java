package com.project.transferapi.interfaces.inbound.http.controller;


import com.project.transferapi.infra.model.UserModel;
import com.project.transferapi.infra.repository.JpaUserRepository;
import com.project.transferapi.interfaces.inbound.http.dto.CreateUserDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.project.transferapi.interfaces.inbound.http.controller.UserFixture.*;

@Testcontainers
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class UserControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    JpaUserRepository jpaUserRepository;

    ObjectMapper mapper = new ObjectMapper();

    @Container
    static MySQLContainer mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.0-debian"));

    @DynamicPropertySource
    static void overrideTestProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

    @BeforeAll
    static void setupContainer() {
        mySQLContainer.start();
    }

    @AfterEach()
    void setup() {
        this.jpaUserRepository.deleteAll();
    }

    @AfterAll
    static void tearDown() {
        mySQLContainer.stop();
    }

    @Test
    void shouldReturn201OnSuccess() throws Exception {
        CreateUserDTO request = getValidCreateUserRequest();

        mockMvc.perform(post("/users")
                        .content(mapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isCreated());

        List<UserModel> allUsers = this.jpaUserRepository.findAll();
        assertFalse(allUsers.isEmpty());
        assertEquals(request.getEmail(), allUsers.get(0).getEmail());
    }

    @Test
    void shouldReturn400WhenPasswordAreNotEquals() throws Exception {
        CreateUserDTO request = getCreateUserDTOWithInvalidPassword();

        mockMvc.perform(post("/users")
                        .content(mapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn409OnDuplicatedEmail() throws Exception {
        UserModel userModel = getUserModelWithDuplicatedEmail();

        this.jpaUserRepository.save(userModel);

        CreateUserDTO request = getValidCreateUserRequest();

        mockMvc.perform(post("/users")
                        .content(mapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isConflict());
    }

    @Test
    void shouldReturn409OnDuplicatedDocument() throws Exception {
        UserModel userModel = getUserModelWithDuplicatedDocument();

        this.jpaUserRepository.save(userModel);

        CreateUserDTO request = getValidCreateUserRequest();

        mockMvc.perform(post("/users")
                        .content(mapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isConflict());
    }


}
