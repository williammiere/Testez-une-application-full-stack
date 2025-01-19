package com.openclassrooms.starterjwt.security.jwt;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@ExtendWith(MockitoExtension.class)
public class JwtUtilsTest {

    @Mock
    private UserDetailsImpl userDetailsImpl;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private JwtUtils jwtUtils;

    private String jwtSecret = "AZERTYUIOPQSDFGHJKLMWXCVBN?1234567890+";
    private int jwtExpirationMs = 100000000;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtUtils, "jwtSecret", jwtSecret); // ReflectionTestUtils is a Spring class used to set the value of a field in a class
        ReflectionTestUtils.setField(jwtUtils, "jwtExpirationMs", jwtExpirationMs);
    }

    @Test
    void testGenerateJwtToken() {
        String mockUsername = "test";
        when(authentication.getPrincipal()).thenReturn(userDetailsImpl); // Mock the getPrincipal method
        when(userDetailsImpl.getUsername()).thenReturn(mockUsername); // Mock the getUsername method

        String resToken = jwtUtils.generateJwtToken(authentication); // Call the method to test

        assertNotNull(resToken);

        String parsedResToken = Jwts.parser() // Parse the token
            .setSigningKey(jwtSecret)
            .parseClaimsJws(resToken)
            .getBody()
            .getSubject();
        
        assertEquals(mockUsername, parsedResToken);

        verify(authentication).getPrincipal(); // Verify that the getPrincipal method was called
        verify(userDetailsImpl).getUsername(); // Verify that the getUsername method was called
    }

    @Test
    void testGetUserNameFromJwtToken() {

        String mockUsername = "test";
        when(authentication.getPrincipal()).thenReturn(userDetailsImpl);
        when(userDetailsImpl.getUsername()).thenReturn(mockUsername);

        String resToken = jwtUtils.generateJwtToken(authentication);
        String res = jwtUtils.getUserNameFromJwtToken(resToken);

        assertEquals(mockUsername, res);

        verify(authentication).getPrincipal();
        verify(userDetailsImpl).getUsername();
    }

    @Test
    void testValidateJwtToken() {

        String validToken = Jwts.builder()
            .setSubject("test")
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();

        assertTrue(jwtUtils.validateJwtToken(validToken));
    }

    @Test
    void testValidateJwtTokenErrorSignature() {

        String notValidToken = Jwts.builder()
            .setSubject("test")
            .signWith(SignatureAlgorithm.HS512, "oops")
            .compact();

        assertFalse(jwtUtils.validateJwtToken(notValidToken));
    }

    @Test
    void testValidateJwtTokenErrorMalformed() {

        String notValid = "notValid";
    
        assertFalse(jwtUtils.validateJwtToken(notValid));
    }

    @Test
    void testValidateJwtTokenErrorExpired() {

        String expiredToken = Jwts.builder()
            .setSubject("test")
            .setExpiration(new Date(System.currentTimeMillis() - 1000))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();

        assertFalse(jwtUtils.validateJwtToken(expiredToken));
    }

    @Test
    void testValidateJwtTokenErrorEmpty() {
        assertFalse(jwtUtils.validateJwtToken(""));
    }
}