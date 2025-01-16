package com.openclassrooms.starterjwt.payload;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.openclassrooms.starterjwt.payload.request.SignupRequest;

public class SignUpRequestTest {
     private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidSignupRequest() {
        SignupRequest request = new SignupRequest();
        request.setEmail("test@test.com");
        request.setFirstName("test");
        request.setLastName("TEST");
        request.setPassword("test!31");

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(request);

        assertTrue(violations.isEmpty(), "Expected no validation violations");
    }

    @Test
    void testInvalidEmail() {
        SignupRequest request = new SignupRequest();
        request.setEmail("badEmail");
        request.setFirstName("test");
        request.setLastName("TEST");
        request.setPassword("test!31");

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty(), "Expected validation to have violations for email");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")),
                "Expected a violation for the email field");
    }

    @Test
    void testEmptyFirstName() {
        SignupRequest request = new SignupRequest();
        request.setEmail("test@test.com");
        request.setFirstName("");
        request.setLastName("TEST");
        request.setPassword("test!31");

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty(), "Expected validation to have violations for firstName");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("firstName")),
                "Expected a violation for the firstName field");
    }

    @Test
    void testTooShortLastName() {
        SignupRequest request = new SignupRequest();
        request.setEmail("test@test.com");
        request.setFirstName("test");
        request.setLastName("A");
        request.setPassword("test!31");

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty(), "Expected validation to have violations for lastName");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("lastName")),
                "Expected a violation for the lastName field");
    }

    @Test
    void testTooShortPassword() {
        SignupRequest request = new SignupRequest();
        request.setEmail("test@test.com");
        request.setFirstName("test");
        request.setLastName("TEST");
        request.setPassword("test");

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty(), "Expected validation to have violations for password");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")),
                "Expected a violation for the password field");
    }

    @Test
    void testEqualSignupRequest() {
        SignupRequest request = new SignupRequest();
        request.setEmail("test@test.com");
        request.setFirstName("test");
        request.setLastName("TEST");
        request.setPassword("test!31");

        SignupRequest request2 = new SignupRequest();
        request2.setEmail("test@test.com");
        request2.setFirstName("test");
        request2.setLastName("TEST");
        request2.setPassword("test!31");

        assertEquals(request, request2, "Two SignupRequest objects with the same properties should be equal");
    }

    @Test
    void testNotEqualSignupRequestDifferentEmail() {
        SignupRequest request = new SignupRequest();
        request.setEmail("test@test.com");
        request.setFirstName("test");
        request.setLastName("TEST");
        request.setPassword("test!31");

        SignupRequest request2 = new SignupRequest();
        request2.setEmail("test2@test.com");
        request2.setFirstName("test");
        request2.setLastName("TEST");
        request2.setPassword("test!31");

        assertNotEquals(request, request2, "SignupRequest objects with different emails should not be equal");
    }

    @Test
    void testNotEqualSignupRequestDifferentFirstName() {
        SignupRequest request = new SignupRequest();
        request.setEmail("test@test.com");
        request.setFirstName("test");
        request.setLastName("TEST");
        request.setPassword("test!31");

        SignupRequest request2 = new SignupRequest();
        request2.setEmail("test@test.com");
        request2.setFirstName("test2");
        request2.setLastName("TEST");
        request2.setPassword("test!31");

        assertNotEquals(request, request2, "SignupRequest objects with different first names should not be equal");
    }

    @Test
    void testNotEqualSignupRequestDifferentLastName() {
        SignupRequest request = new SignupRequest();
        request.setEmail("test@test.com");
        request.setFirstName("test");
        request.setLastName("TEST");
        request.setPassword("test!31");

        SignupRequest request2 = new SignupRequest();
        request2.setEmail("test@test.com");
        request2.setFirstName("test");
        request2.setLastName("TEST2");
        request2.setPassword("test!31");

        assertNotEquals(request, request2, "SignupRequest objects with different last names should not be equal");
    }

    @Test
    void testNotEqualSignupRequestDifferentPassword() {
        SignupRequest request = new SignupRequest();
        request.setEmail("test@test.com");
        request.setFirstName("test");
        request.setLastName("TEST");
        request.setPassword("test!31");

        SignupRequest request2 = new SignupRequest();
        request2.setEmail("test@test.com");
        request2.setFirstName("test");
        request2.setLastName("TEST");
        request2.setPassword("test!32");

        assertNotEquals(request, request2, "SignupRequest objects with different passwords should not be equal");
    }

    @Test
    void testNotEqualSignupRequestNull() {
        SignupRequest request = new SignupRequest();
        request.setEmail("test@test.com");
        request.setFirstName("test");
        request.setLastName("TEST");
        request.setPassword("test!31");

        assertNotEquals(request, null, "SignupRequest object should not be equal to null");
    }

    @Test
    void testNotEqualSignupRequestDifferentClass() {
        SignupRequest request = new SignupRequest();
        request.setEmail("test@test.com");
        request.setFirstName("test");
        request.setLastName("TEST");
        request.setPassword("test!31");

        assertNotEquals(request, new Object(), "SignupRequest object should not be equal to an object of a different class");
    }

    // Hascode
    @Test
    void testHashCodeEqualObjects() {
        SignupRequest request = new SignupRequest();
        request.setEmail("test@test.com");
        request.setFirstName("test");
        request.setLastName("TEST");
        request.setPassword("test!31");

        SignupRequest request2 = new SignupRequest();
        request2.setEmail("test@test.com");
        request2.setFirstName("test");
        request2.setLastName("TEST");
        request2.setPassword("test!31");

        assertEquals(request.hashCode(), request2.hashCode(), "Two equal SignupRequest objects should have the same hashCode");
    }

    @Test
    void testHashCodeDifferentObjects() {
        SignupRequest request = new SignupRequest();
        request.setEmail("test@test.com");
        request.setFirstName("test");
        request.setLastName("TEST");
        request.setPassword("test!31");

        SignupRequest request2 = new SignupRequest();
        request2.setEmail("test2@test.com");
        request2.setFirstName("test2");
        request2.setLastName("TEST2");
        request2.setPassword("test!32");

        assertNotEquals(request.hashCode(), request2.hashCode(), "Two different SignupRequest objects should have different hashCodes");
    }

    @Test
    void testHashCodeConsistency() {
        SignupRequest request = new SignupRequest();
        request.setEmail("test@test.com");
        request.setFirstName("test");
        request.setLastName("TEST");
        request.setPassword("test!31");

        int initialHashCode = request.hashCode();
        assertEquals(initialHashCode, request.hashCode(), "The hashCode should be the same when called multiple times on the same object");
    }

    @Test
    void testHashCodeWithNullFields() {
        SignupRequest request = new SignupRequest();
        request.setEmail("test@test.com");
        request.setFirstName(null);
        request.setLastName("TEST");
        request.setPassword("test!31");

        SignupRequest request2 = new SignupRequest();
        request2.setEmail("test@test.com");
        request2.setFirstName(null);
        request2.setLastName("TEST");
        request2.setPassword("test!31");

        assertEquals(request.hashCode(), request2.hashCode(), "Two SignupRequest objects with same properties (including null) should have the same hashCode");
    }
}