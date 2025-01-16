package com.openclassrooms.starterjwt.controllers;

import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.transaction.annotation.Transactional;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TeacherControllerIntegrationTest {

    @Autowired
    private MockMvc mockmMvc;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    @WithMockUser(username = "test@test.com") // Uses mockMvc to mock an user into the spring security context
    void testSuccessFindById() throws Exception {
        Teacher teacher = new Teacher();
        teacher.setFirstName("teacher");
        teacher.setLastName("TEST");
        teacherRepository.save(teacher);
        
        TeacherDto expectedTeacherDto = teacherMapper.toDto(teacher);

        mockmMvc.perform(get("/api/teacher/" + teacher.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName", is(expectedTeacherDto.getFirstName())))
            .andExpect(jsonPath("$.lastName", is(expectedTeacherDto.getLastName())));
    }

    @Test
    @WithMockUser(username = "test@test.com")
    void testFailureFindById() throws Exception {
        mockmMvc.perform(get("/api/teacher/invalid")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "test@test.com")
    void testSuccessFindAll() throws Exception {

        Teacher teacher = new Teacher();
        teacher.setFirstName("teacherONE");
        teacher.setLastName("TEACHERONE");

        Teacher teacher2 = new Teacher();
        teacher2.setFirstName("teacherTWO");
        teacher2.setLastName("TEACHERTWO");
            
        teacherRepository.save(teacher);
        teacherRepository.save(teacher2);

        mockmMvc.perform(get("/api/teacher/")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[?(@.firstName == '%s' && @.lastName == '%s')]", "teacherONE", "TEACHERONE").exists()) // Searches in the JSON response for the teacherONE and TEACHERONE values
            .andExpect(jsonPath("$[?(@.firstName == '%s' && @.lastName == '%s')]", "teacherTWO", "TEACHERTWO").exists());
    }
}