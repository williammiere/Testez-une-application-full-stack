package com.openclassrooms.starterjwt.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.repository.SessionRepository;

@ExtendWith(MockitoExtension.class)
public class SessionServiceTest {

    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private SessionService sessionService;

    @Test
    public void testCreate() {
        Session session = new Session();
        session.setId(1L);
        session.setName("sessionTest");
        session.setDescription("Description Test");
        
        when(sessionRepository.save(any(Session.class))).thenReturn(session);

        Session result = sessionService.create(session);

        verify(sessionRepository, times(1)).save(any(Session.class));
        Assertions.assertEquals(result.getId(), session.getId());
    }

    @Test
    public void testDelete() {
        sessionService.delete(1L);
        verify(sessionRepository, times(1)).deleteById(1L);
    }
    
}
