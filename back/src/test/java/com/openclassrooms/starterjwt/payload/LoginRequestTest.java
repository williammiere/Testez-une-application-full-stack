package com.openclassrooms.starterjwt.payload;

import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginRequestTest {
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequest();
    }

    @Test
    void shouldSetAndGetEmail() {
        String email = "test@test.com";
        loginRequest.setEmail(email);
        assertEquals(email, loginRequest.getEmail());
    }

    @Test
    void shouldSetAndGetPassword() {
        String password = "test!31";
        loginRequest.setPassword(password);
        assertEquals(password, loginRequest.getPassword());
    }
}