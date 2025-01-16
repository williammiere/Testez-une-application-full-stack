package com.openclassrooms.starterjwt.services;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock // Create a mock of the UserRepository
    private UserRepository userRepository;

    @InjectMocks // Inject the mock into the UserService
    private UserService userService;

    @Test
    public void testFindUser(){
        User userTest = new User();
        userTest.setId(1L);
        userTest.setAdmin(true);

        when(userRepository.findById(1L)).thenReturn(Optional.of(userTest)); // Mock the findById method

        User userResult = userService.findById(1L);

        verify(userRepository, times(1)).findById(1L); // Verify that the findById method was called once
        
        Assertions.assertEquals(userResult.getId(), userTest.getId());
        Assertions.assertEquals(userResult.isAdmin(), userTest.isAdmin());
    }

    @Test
    public void testDelete() {
        userService.delete(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }
}
