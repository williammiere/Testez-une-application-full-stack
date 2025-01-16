package com.openclassrooms.starterjwt.models;

import java.time.LocalDateTime;
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

public class TeacherTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testTeacherValidData() {
        Teacher teacher = new Teacher();
        teacher.setFirstName("test");
        teacher.setLastName("TEST");

        Set<ConstraintViolation<Teacher>> violations = validator.validate(teacher);
        assertThat(violations).isEmpty();
    }

    @Test
    public void testTeacherNoFirstName() {
        Teacher teacher = new Teacher();
        teacher.setLastName("TEST");

        Set<ConstraintViolation<Teacher>> violations = validator.validate(teacher);

        assertFalse(violations.isEmpty(), "Expected validation to have violations");
        assertTrue(violations.stream()
            .anyMatch(violation -> violation.getPropertyPath().toString().equals("firstName")),
            "Expected a violation for the firstName field");
    }

    @Test
    public void testTeacherNoLastName() {
        Teacher teacher = new Teacher();
        teacher.setFirstName("John");

        Set<ConstraintViolation<Teacher>> violations = validator.validate(teacher);

        assertFalse(violations.isEmpty(), "Expected validation to have violations");
        assertTrue(violations.stream()
            .anyMatch(violation -> violation.getPropertyPath().toString().equals("lastName")),
            "Expected a violation for the lastName field");
    }

    @Test
    public void testTeacherTooLongFirstName() {
        Teacher teacher = new Teacher();
        teacher.setFirstName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        teacher.setLastName("TEST");

        Set<ConstraintViolation<Teacher>> violations = validator.validate(teacher);
        
        assertFalse(violations.isEmpty(), "Expected validation to have violations");
        assertTrue(violations.stream()
            .anyMatch(violation -> violation.getPropertyPath().toString().equals("firstName")),
            "Expected a violation for the firstName field due to size limit");
    }

    @Test
    public void testTeacherTooLongLastName() {
        Teacher teacher = new Teacher();
        teacher.setFirstName("test");
        teacher.setLastName("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

        Set<ConstraintViolation<Teacher>> violations = validator.validate(teacher);

        assertFalse(violations.isEmpty(), "Expected validation to have violations");
        assertTrue(violations.stream()
            .anyMatch(violation -> violation.getPropertyPath().toString().equals("lastName")),
            "Expected a violation for the lastName field due to size limit");
    }

    @Test
    void testEquals_Self() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setLastName("TEST");
        teacher.setFirstName("test");
        teacher.setCreatedAt(LocalDateTime.now());
        teacher.setUpdatedAt(LocalDateTime.now());

        assertEquals(teacher, teacher, "The same Teacher object should be equal to itself");
    }

    @Test
    void testEquals_SameIds() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setLastName("TEST");
        teacher.setFirstName("test");
        teacher.setCreatedAt(LocalDateTime.now());
        teacher.setUpdatedAt(LocalDateTime.now());

        Teacher teacher2 = new Teacher();
        teacher2.setId(1L);
        teacher2.setLastName("TEST2");
        teacher2.setFirstName("test2");
        teacher2.setCreatedAt(LocalDateTime.now());
        teacher2.setUpdatedAt(LocalDateTime.now());
        assertEquals(teacher, teacher2, "Two Teacher objects with the same Id should be equal");
    }

    @Test
    void testEquals_DifferentObjects() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setLastName("TEST");
        teacher.setFirstName("test");
        teacher.setCreatedAt(LocalDateTime.now());
        teacher.setUpdatedAt(LocalDateTime.now());

        Teacher teacher2 = new Teacher();
        teacher2.setId(2L);
        teacher2.setLastName("TEST2");
        teacher2.setFirstName("test2");
        teacher2.setCreatedAt(LocalDateTime.now());
        teacher2.setUpdatedAt(LocalDateTime.now());
        assertNotEquals(teacher, teacher2, "Two Teacher objects with different Ids should not be equal");
    }

    @Test
    void testEquals_NullObject() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setLastName("TEST");
        teacher.setFirstName("test");
        teacher.setCreatedAt(LocalDateTime.now());
        teacher.setUpdatedAt(LocalDateTime.now());
        assertNotEquals(teacher, null, "A Teacher object should not be null");
    }

    @Test
    void testEquals_DifferentClass() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setLastName("TEST");
        teacher.setFirstName("test");
        teacher.setCreatedAt(LocalDateTime.now());
        teacher.setUpdatedAt(LocalDateTime.now());
        String otherObject = "Not a Teacher";
        assertNotEquals(teacher, otherObject, "A Teacher object should not be equal to an object of a different class");
    }

    @Test
    void testEquals_NullFields() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setLastName(null);
        teacher.setFirstName(null);
        teacher.setCreatedAt(null);
        teacher.setUpdatedAt(null);

        Teacher teacher2 = new Teacher();
        teacher2.setId(1L);
        teacher2.setLastName(null);
        teacher2.setFirstName(null);
        teacher2.setCreatedAt(null);
        teacher2.setUpdatedAt(null);

        assertEquals(teacher, teacher2, "Two Teacher objects with the same Id should be equal");
    }

    @Test
    void testHashCode_EqualObjects() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setLastName("TEST");
        teacher.setFirstName("test");
        teacher.setCreatedAt(LocalDateTime.now());
        teacher.setUpdatedAt(LocalDateTime.now());

        Teacher teacher2 = new Teacher();
        teacher2.setId(1L);
        teacher2.setLastName("TEST");
        teacher2.setFirstName("test");
        teacher2.setCreatedAt(LocalDateTime.now());
        teacher2.setUpdatedAt(LocalDateTime.now());

        assertEquals(teacher.hashCode(), teacher2.hashCode(), "Two equal Teacher objects should have the same hashCode");
    }

    @Test
    void testHashCode_DifferentObjects() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setLastName("TEST");
        teacher.setFirstName("test");
        teacher.setCreatedAt(LocalDateTime.now());
        teacher.setUpdatedAt(LocalDateTime.now());

        Teacher teacher2 = new Teacher();
        teacher2.setId(2L);
        teacher2.setLastName("TEST2");
        teacher2.setFirstName("test2");
        teacher2.setCreatedAt(LocalDateTime.now());
        teacher2.setUpdatedAt(LocalDateTime.now());

        assertNotEquals(teacher.hashCode(), teacher2.hashCode(), "Two different Teacher objects should have different hashCodes");
    }

    @Test
    void testHashCode_Self() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setLastName("TEST");
        teacher.setFirstName("test");
        teacher.setCreatedAt(LocalDateTime.now());
        teacher.setUpdatedAt(LocalDateTime.now());

        int initialHashCode = teacher.hashCode();
        assertEquals(initialHashCode, teacher.hashCode(), "The hashCode should be the same when called multiple times on the same object");
    }

    @Test
    void testHashCode_NullFields() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setLastName("TEST");
        teacher.setFirstName("test");
        teacher.setCreatedAt(LocalDateTime.now());
        teacher.setUpdatedAt(LocalDateTime.now());
        
        Teacher teacher2 = new Teacher();
        teacher2.setId(1L);
        teacher2.setLastName(null);
        teacher2.setFirstName(null);
        teacher2.setCreatedAt(null);
        teacher2.setUpdatedAt(null);
        assertEquals(teacher.hashCode(), teacher2.hashCode(), "Two Teacher objects with the same id should have the same hashCode");
    }

    @Test
    void testHashCode_DifferentFields() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setLastName("TEST");
        teacher.setFirstName("test");
        teacher.setCreatedAt(LocalDateTime.now());
        teacher.setUpdatedAt(LocalDateTime.now());
        
        Teacher teacher2 = new Teacher();
        teacher2.setId(1L);
        teacher2.setLastName("TEST2");
        teacher2.setFirstName("test2");
        teacher2.setCreatedAt(LocalDateTime.now());
        teacher2.setUpdatedAt(LocalDateTime.now());

        assertEquals(teacher.hashCode(), teacher2.hashCode(), "Two Teacher objects with the same Id should have the same hashCode");
    }
}