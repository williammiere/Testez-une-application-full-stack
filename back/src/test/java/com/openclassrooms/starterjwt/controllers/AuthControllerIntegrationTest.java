package com.openclassrooms.starterjwt.controllers;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;

@SpringBootTest // Launch the Spring Boot application with all the Spring context
@AutoConfigureMockMvc // Eases the HTTP request sending by mocking the HTTP requests
@Transactional // Rollbacks the changes in the database after the test to keep the database unchanged
public class AuthControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc; // Is used to mock the HTTP requests

    @Autowired
    private ObjectMapper objectMapper; // Is used to convert objects to JSON and vice versa

    @Test
    public void testRegister() throws Exception {

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("test@test.com");
        signupRequest.setPassword("test!31");
        signupRequest.setFirstName("test");
        signupRequest.setLastName("TEST");

        String jsonRequest = objectMapper.writeValueAsString(signupRequest);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk());
    }

     // Test the login request
     // The user with email yoga@studio.com and password test!1234 must exist in db.
    @Test
    public void testLogin() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("yoga@studio.com");
        loginRequest.setPassword("test!1234");
        String jsonRequest = objectMapper.writeValueAsString(loginRequest);

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk());
    }
}
