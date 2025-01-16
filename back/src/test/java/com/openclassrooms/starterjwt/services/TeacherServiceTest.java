package com.openclassrooms.starterjwt.services;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherService teacherService;

    @Test
    public void testFindAll() {
        
        List<Teacher> teachersTest = List.of(new Teacher(), new Teacher(), new Teacher());

        when(teacherRepository.findAll()).thenReturn(teachersTest);

        List<Teacher> teachersResult = teacherService.findAll();

        Assertions.assertEquals(teachersResult.size(), teachersTest.size());
    }

    @Test
    public void testFindById() {
        Teacher teacherTest = new Teacher();
        teacherTest.setId(1L);

        when(teacherRepository.findById(1L)).thenReturn(java.util.Optional.of(teacherTest));

        Teacher teacherResult = teacherService.findById(1L);

        Assertions.assertEquals(teacherResult.getId(), teacherTest.getId());
    }
    
}
