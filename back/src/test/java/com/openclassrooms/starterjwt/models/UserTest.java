package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testUserValid() {
        User user = new User();
        user.setEmail("test@test.com");
        user.setFirstName("test");
        user.setLastName("TEST");
        user.setPassword("test!31");
        user.setAdmin(true);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testEmailInvalid() {
        User user = new User();
        user.setEmail("not.an-email");
        user.setFirstName("test");
        user.setLastName("TEST");
        user.setPassword("test!31");
        user.setAdmin(true);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("doit être une adresse électronique syntaxiquement correcte", violations.iterator().next().getMessage());
    }

    @Test
    public void testFirstNameSize() {
        User user = new User();
        user.setEmail("test@test.com");
        user.setFirstName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        user.setLastName("TEST");
        user.setPassword("test!31");
        user.setAdmin(true);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("la taille doit être comprise entre 0 et 20", violations.iterator().next().getMessage());
    }

    @Test
    public void testLastNameSize() {
        User user = new User();
        user.setEmail("test@test.com");
        user.setFirstName("test");
        user.setLastName("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        user.setPassword("test!31");
        user.setAdmin(true);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("la taille doit être comprise entre 0 et 20", violations.iterator().next().getMessage());
    }
    
    @Test
    public void testGettersAndSetters() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");
        user.setFirstName("test");
        user.setLastName("TEST");
        user.setPassword("test!31");
        user.setAdmin(true);

        assertEquals(1L, user.getId());
        assertEquals("test@test.com", user.getEmail());
        assertEquals("test", user.getFirstName());
        assertEquals("TEST", user.getLastName());
        assertEquals("test!31", user.getPassword());
        assertTrue(user.isAdmin());
    }

}
