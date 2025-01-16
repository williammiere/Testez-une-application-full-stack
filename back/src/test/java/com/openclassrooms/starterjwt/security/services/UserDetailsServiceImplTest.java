package com.openclassrooms.starterjwt.security.services;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Mock
    private UserRepository userRepository;

    @Mock
    private User mockUser;

    @Test
    void testLoadUserByUsername() {
        String mockUsername = "test";
        when(userRepository.findByEmail(mockUsername)).thenReturn(Optional.of(mockUser));
        when(mockUser.getId()).thenReturn(1L);
        when(mockUser.getEmail()).thenReturn("test@test.com");
        when(mockUser.getLastName()).thenReturn("TEST");
        when(mockUser.getFirstName()).thenReturn("test");
        when(mockUser.getPassword()).thenReturn("test!31");

        UserDetails res = userDetailsServiceImpl.loadUserByUsername(mockUsername);

        assertEquals("test@test.com", res.getUsername());
        assertEquals("test!31", res.getPassword());
    }

    @Test
    void testLoadUserByUsernameNotFound() {
        String mockUsername = "test";
        when(userRepository.findByEmail(mockUsername)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsServiceImpl.loadUserByUsername(mockUsername); // Calls the method and gets the exception
        });
        assertEquals("User Not Found with email: test", exception.getMessage());
    }
}