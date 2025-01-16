package com.openclassrooms.starterjwt.models;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SessionTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testSessionValidData() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);

        Session session = new Session();
        session.setId(1L);
        session.setName("Test Session");
        session.setDate(new Date());
        session.setDescription("Description");
        session.setTeacher(teacher);
        session.setUsers(List.of());

        Set<ConstraintViolation<Session>> violations = validator.validate(session);

        assertThat(violations).isEmpty();
    }

    @Test
    public void testSessionNoName() {
        Session session = new Session();
        session.setId(1L);
        session.setDate(new Date());
        session.setDescription("Description");

        Set<ConstraintViolation<Session>> violations = validator.validate(session);
        
        assertFalse(violations.isEmpty(), "Expected validation to have violations");
        assertTrue(violations.stream()
            .anyMatch(violation -> violation.getPropertyPath().toString().equals("name")),
            "Expected a violation for the name field");
    }

    @Test
    public void testSessionTooLongName() {
        Session session = new Session();
        session.setId(1L);
        session.setName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        session.setDate(new Date());
        session.setDescription("Description");

        Set<ConstraintViolation<Session>> violations = validator.validate(session);
        
        assertFalse(violations.isEmpty(), "Expected validation to have violations");
        assertTrue(violations.stream()
            .anyMatch(violation -> violation.getPropertyPath().toString().equals("name")),
            "Expected a violation for the name field due to size limit");
    }

    @Test
    public void testSessionNoDate() {
        Session session = new Session();
        session.setId(1L);
        session.setName("Test Session");
        session.setDescription("Description");
        Set<ConstraintViolation<Session>> violations = validator.validate(session);
        
        assertFalse(violations.isEmpty(), "Expected validation to have violations");
        assertTrue(violations.stream()
            .anyMatch(violation -> violation.getPropertyPath().toString().equals("date")),
            "Expected a violation for the date field");
    }

    @Test
    public void testSessionNoDescription() {
        Session session = new Session();
        session.setId(1L);
        session.setName("Test Session");
        session.setDate(new Date());

        Set<ConstraintViolation<Session>> violations = validator.validate(session);

        assertFalse(violations.isEmpty(), "Expected validation to have violations");
        assertTrue(violations.stream()
            .anyMatch(violation -> violation.getPropertyPath().toString().equals("description")),
            "Expected a violation for the description field");
    }

    @Test
    void testEquals_WithItself() {
        Session session = new Session();
        session.setId(1L);
        session.setName("Test Session");
        session.setDate(new Date());
        session.setDescription("Description");
        assertEquals(session, session, "The same Session object should be equal to itself");
    }

    @Test
    void testEquals_EqualObjects() {
        Session session = new Session();
        session.setId(1L);
        session.setName("Test Session");
        session.setDate(new Date());
        session.setDescription("Description");

        Session session2 = new Session();
        session2.setId(1L);
        session2.setName("Test Session");
        session2.setDate(new Date());
        session2.setDescription("Description");

        assertEquals(session, session2, "Two Session objects with the same Id should be equal");
    }

    @Test
    void testEquals_DifferentObjects() {
        Session session = new Session();
        session.setId(1L);
        session.setName("Test Session");
        session.setDate(new Date());
        session.setDescription("Description");

        Session session2 = new Session();
        session2.setId(2L);
        session2.setName("Test Session 2");
        session2.setDate(new Date());
        session2.setDescription("Description 2");

        assertNotEquals(session, session2, "Two Session objects with different Ids should not be equal");
    }

    @Test
    void testEquals_NullObject() {
        Session session = new Session();
        session.setId(1L);
        session.setName("Test Session");
        session.setDate(new Date());
        session.setDescription("Description");
        assertNotEquals(session, null, "A Session object should not be null");
    }

    @Test
    void testEquals_DifferentClass() {
        Session session = new Session();
        session.setId(1L);
        session.setName("Test Session");
        session.setDate(new Date());
        session.setDescription("Description");

        String otherObject = "Not a session";
        assertNotEquals(session, otherObject, "A Session object should not be equal to an object of a different class");
    }

    @Test
    void testEquals_NullExceptId() {
        Session session = new Session();
        session.setId(1L);

        Session session2 = new Session();
        session2.setId(1L);

        assertEquals(session, session2, "Two Session objects with the same ID and null fields should be equal");
    }

    @Test
    void testHashCode_EqualObjects() {
        Session session = new Session();
        session.setId(1L);
        session.setName("Test Session");
        session.setDate(new Date());
        session.setDescription("Description");

        Session session2 = new Session();
        session2.setId(1L);
        session2.setName("Test Session");
        session2.setDate(new Date());
        session2.setDescription("Description");

        assertEquals(session.hashCode(), session2.hashCode(), "Two equal Session objects should have the same hashCode");
    }

    @Test
    void testHashCode_DifferentObjects() {
        Session session = new Session();
        session.setId(1L);
        session.setName("Test Session");
        session.setDate(new Date());
        session.setDescription("Description");

        Session session2 = new Session();
        session2.setId(2L);
        session2.setName("Test Session 2");
        session2.setDate(new Date());
        session2.setDescription("Description 2");

        assertNotEquals(session.hashCode(), session2.hashCode(), "Two different Session objects should have different hashCodes");
    }

    @Test
    void testHashCode_Self() {
        Session session = new Session();
        session.setId(1L);
        session.setName("Test Session");
        session.setDate(new Date());
        session.setDescription("Description");

        int initialHashCode = session.hashCode();
        assertEquals(initialHashCode, session.hashCode(), "The hashCode should be the same when called multiple times on the same object");
    }

    @Test
    void testHashCode_DifferentFieldsSameID() {
        Session session = new Session();
        session.setId(1L);
        session.setName("Test Session");
        session.setDate(new Date());
        session.setDescription("Description");

        Session session2 = new Session();
        session2.setId(1L);
        session2.setName("Test Session 2");
        session2.setDate(new Date());
        session2.setDescription("Description 2");
        assertEquals(session.hashCode(), session2.hashCode(), "Two Session objects with the same ID but different fields should have the same hashCode");
    }

}