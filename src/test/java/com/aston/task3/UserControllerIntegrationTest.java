package com.aston.task3;

import com.aston.task2.dto.EmailDto;
import com.aston.task2.dto.UserDto;
import com.aston.task2.entity.User;
import com.aston.task2.repository.UserRepository;
import com.aston.task2.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class UserControllerIntegrationTest {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void createUser() throws Exception {
        UserDto UserDto = new UserDto().setName("Test User").setEmail("test@example.com").setAge(30);

        mockMvc.perform(post("/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(UserDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void getUserById() throws Exception {
        User user = new User().setName("Test User").setEmail("test@example.com").setAge(30);
        user = userRepository.save(user);

        mockMvc.perform(get("/users/{id}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.name").value("Test User"));
    }

    @Test
    void getUserByEmail() throws Exception {
        User user = new User().setName("Test User").setEmail("test@example.com").setAge(30);
        user = userRepository.save(user);

        EmailDto emailDto = new EmailDto().setEmail("test@example.com");

        mockMvc.perform(get("/users/getByEmail")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emailDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    void updateUser() throws Exception {
        User user = new User().setName("Old Name").setEmail("old@test.com").setAge(25);
        user = userRepository.save(user);

        UserDto userDto = new UserDto().setName("New Name").setEmail("new@test.com").setAge(30);

        mockMvc.perform(put("/users/{id}", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Name"))
                .andExpect(jsonPath("$.email").value("new@test.com"));
    }

    @Test
    void deleteUser() throws Exception {
        User user = new User().setName("To Delete").setEmail("delete@test.com").setAge(40);
        user = userRepository.save(user);

        mockMvc.perform(delete("/users/{id}", user.getId()))
                .andExpect(status().isNoContent());

        assertFalse(userRepository.existsById(user.getId()));
    }
}
