package com.openclassrooms.starterjwt.controllers;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.transaction.annotation.Transactional;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;

@SpringBootTest // Launch the Spring Boot application with all the Spring context
@AutoConfigureMockMvc // Eases the HTTP request sending by mocking the HTTP requests
@Transactional // Rollbacks the changes in the database after the test to keep the database unchanged
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc; // Is used to mock the HTTP requests

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Test
    @WithMockUser(username = "test@test.com") // Uses mockMvc to mock an user into the spring security context
    void testSuccessFindById() throws Exception {
        User user = new User();
            user.setEmail("test@test.com");
            user.setFirstName("test");
            user.setLastName("TEST");
        userRepository.save(user);

        UserDto expectedUserDto = userMapper.toDto(user);

        mockMvc.perform(get("/api/user/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(expectedUserDto.getEmail())))
                .andExpect(jsonPath("$.firstName", is(expectedUserDto.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(expectedUserDto.getLastName())));
    }

    @Test
    @WithMockUser(username = "test@test.com")
    void testFailureFindById() throws Exception { // Expects Bad Request status
       mockMvc.perform(get("/api/user/invalid")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "test@test.com")
    void testSuccessDeleteById() throws Exception {

        User user = new User();
        user.setEmail("test@test.com");
        user.setFirstName("test");
        user.setLastName("TEST");
        userRepository.save(user);


        mockMvc.perform(delete("/api/user/" + user.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        Optional<User> deletedUser = userRepository.findById(user.getId());
        assertTrue(deletedUser.isEmpty());
    }

    @Test
    @WithMockUser(username = "test@test.com")
    void testFailureDeleteById() throws Exception {
        mockMvc.perform(delete("/api/user/invalid")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }
}
