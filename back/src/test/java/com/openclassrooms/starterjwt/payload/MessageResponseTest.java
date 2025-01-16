package com.openclassrooms.starterjwt.payload;

import com.openclassrooms.starterjwt.payload.response.MessageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageResponseTest {
    private MessageResponse messageResponse;

    @BeforeEach
    void setUp() {
        messageResponse = new MessageResponse("Test message");
    }

    @Test
    void shouldSetAndGetMessage() {
        String message = "New test message";
        messageResponse.setMessage(message);
        assertEquals(message, messageResponse.getMessage());
    }
}