package com.openclassrooms.starterjwt.payload;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.openclassrooms.starterjwt.payload.response.JwtResponse;

class JwtResponseTest {
    private JwtResponse jwtResponse;

    @BeforeEach
    void setUp() {
        jwtResponse = new JwtResponse("token", 1L, "test", "test", "TEST", true);
    }

    @Test
    void shouldSetAndGetToken() {
        String token = "newToken";
        jwtResponse.setToken(token);
        assertEquals(token, jwtResponse.getToken());
    }

    @Test
    void shouldSetAndGetType() {
        String type = "newType";
        jwtResponse.setType(type);
        assertEquals(type, jwtResponse.getType());
    }

    @Test
    void shouldSetAndGetId() {
        Long id = 2L;
        jwtResponse.setId(id);
        assertEquals(id, jwtResponse.getId());
    }

    @Test
    void shouldSetAndGetUsername() {
        String username = "new TEST";
        jwtResponse.setUsername(username);
        assertEquals(username, jwtResponse.getUsername());
    }

    @Test
    void shouldSetAndGetFirstName() {
        String firstName = "new TEST";
        jwtResponse.setFirstName(firstName);
        assertEquals(firstName, jwtResponse.getFirstName());
    }

    @Test
    void shouldSetAndGetLastName() {
        String lastName = "new TEST";
        jwtResponse.setLastName(lastName);
        assertEquals(lastName, jwtResponse.getLastName());
    }

    @Test
    void shouldSetAndGetAdminStatus() {
        jwtResponse.setAdmin(false);
        assertFalse(jwtResponse.getAdmin());
    }
}