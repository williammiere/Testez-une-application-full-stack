package com.openclassrooms.starterjwt.security.services;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;

@ExtendWith(MockitoExtension.class)
public class UserDetailsImplTest {

    @InjectMocks
    private UserDetailsImpl userDetailsImpl;

    @Test
    void testGetAuthorities() { // Should return an empty list of authorities
        Collection<? extends GrantedAuthority> authorities = userDetailsImpl.getAuthorities();

        assertTrue(authorities.isEmpty(), "Authorities should be empty");
        assertEquals(0, authorities.size(), "Authorities size should be 0");
    }

    @Test
    void testIsAccountNonExpired() {
        assertTrue(userDetailsImpl.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {
        assertTrue(userDetailsImpl.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {
        assertTrue(userDetailsImpl.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        assertTrue(userDetailsImpl.isEnabled());
    }

    @Test
    void testIsEqual() {
        UserDetailsImpl user = new UserDetailsImpl();
        user.setId(1L);
        user.setUsername("test");
        user.setFirstName("test");
        user.setLastName("TEST");
        user.setAdmin(false);
        user.setPassword("test!31");

        assertTrue(user.equals(user));
    }

    @Test
    void testIsNotEqualNull() {

        UserDetailsImpl user = new UserDetailsImpl();
        user.setId(1L);
        user.setUsername("test");
        user.setFirstName("test");
        user.setLastName("TEST");
        user.setAdmin(false);   
        user.setPassword("test!31");

        assertFalse(user.equals(null));
    }

    @Test
    void testIsNotEqualDifferentClass() {
        UserDetailsImpl user = new UserDetailsImpl();
        user.setId(1L);
        user.setUsername("test");
        user.setFirstName("test");
        user.setLastName("TEST");
        user.setAdmin(false);
        user.setPassword("test!31");

        Object differentClassObject = new Object();
        assertFalse(user.equals(differentClassObject));
    }

    @Test
    void testIsNotEqualDifferentId() {
        UserDetailsImpl user1 = new UserDetailsImpl();
        user1.setId(1L);
        user1.setUsername("test");
        user1.setFirstName("test");
        user1.setLastName("TEST");
        user1.setAdmin(false);
        user1.setPassword("test!31");
        UserDetailsImpl user2 = new UserDetailsImpl();
        user2.setId(2L);
        user2.setUsername("test");
        user2.setFirstName("test");
        user2.setLastName("TEST");
        user2.setAdmin(false);
        user2.setPassword("test!31");

        assertFalse(user1.equals(user2));
    }

    @Test
    void testIsEqualSameId() {

        UserDetailsImpl user1 = new UserDetailsImpl();
        user1.setId(1L);
        user1.setUsername("test");
        user1.setFirstName("test");
        user1.setLastName("TEST");
        user1.setAdmin(false);
        user1.setPassword("test!31");

        UserDetailsImpl user2 = new UserDetailsImpl();
        user2.setId(1L);
        user2.setUsername("test2");
        user2.setFirstName("test2");
        user2.setLastName("TEST2");
        user2.setAdmin(true);
        user2.setPassword("test!32");

        assertTrue(user1.equals(user2));
    }
}