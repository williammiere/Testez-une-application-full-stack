package com.openclassrooms.starterjwt.controllers;

import java.util.ArrayList;
import java.util.Date;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SessionControllerIntegrationTest {

    @Autowired
    private SessionMapper sessionMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    @WithMockUser(username = "test@test.com") // Uses mockMvc to mock an user into the spring security context
    void testSuccessFindById() throws Exception {

        Session session = new Session();
        session.setName("testSession");
        session.setDate(new Date());
        session.setDescription("description");
        sessionRepository.save(session);
        SessionDto sessionDto = sessionMapper.toDto(session);

        mockMvc.perform(get("/api/session/" + session.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is(sessionDto.getName())));
    }

    @Test
    @WithMockUser(username = "test@test.com")
    void testFailureFindById() throws Exception {
        mockMvc.perform(get("/api/session/invalid")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "test@test.com")
    void testFindAll() throws Exception {

        sessionRepository.deleteAll(); // clear existing sessions and create new ones
        Session session = new Session();
        session.setName("Session1");
        session.setDate(new Date());
        session.setDescription("description1");

        Session session2 = new Session();
        session2.setName("Session2");
        session2.setDate(new Date());
        session2.setDescription("description2");
        sessionRepository.save(session);
        sessionRepository.save(session2);

        mockMvc.perform(get("/api/session/")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[?(@.name == '%s')]", "Session1").exists()) // Searches in the JSON response for the session name
            .andExpect(jsonPath("$[?(@.name == '%s')]", "Session2").exists());
    }

    @Test
    @WithMockUser(username = "test@test.com")
    void testCreate() throws Exception {

        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("SessionTEST");
        sessionDto.setDate(new Date());
        sessionDto.setTeacher_id(1L);
        sessionDto.setDescription("DescriptionTEST");


        mockMvc.perform(post("/api/session/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(sessionDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is(sessionDto.getName())))
            .andExpect(jsonPath("$.description", is(sessionDto.getDescription())));
    }

    @Test
    @WithMockUser(username = "test@test.com")
    void testUpdate() throws Exception {

        Session session = new Session();
        session.setName("Session");
        session.setDate(new Date());
        session.setDescription("Description");
        sessionRepository.save(session);

        Teacher teacher = new Teacher();
        teacher.setFirstName("teacher");
        teacher.setLastName("TEACHER");
        teacherRepository.save(teacher);

        SessionDto updatedSessionDto = new SessionDto();
        updatedSessionDto.setName("NewSession");
        updatedSessionDto.setDate(new Date());
        updatedSessionDto.setTeacher_id(teacher.getId());
        updatedSessionDto.setDescription("Description2");

        mockMvc.perform(put("/api/session/" + session.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(updatedSessionDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is(updatedSessionDto.getName())))
            .andExpect(jsonPath("$.description", is(updatedSessionDto.getDescription())));
    }

    @Test
    @WithMockUser(username = "test@test.com")
    void testDelete_shouldReturnOk() throws Exception {
        Session session = new Session();
        session.setName("Session");
        session.setDate(new Date());
        session.setDescription("Description");
        sessionRepository.save(session);

        mockMvc.perform(delete("/api/session/" + session.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        mockMvc.perform(get("/api/session/" + session.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "test@test.com")
    void testParticipate() throws Exception {

        Session session = new Session();
        session.setName("Session");
        session.setDate(new Date());
        session.setDescription("Description");
        sessionRepository.save(session);

        User user = new User();
        user.setLastName("test");
        user.setFirstName("TEST");
        user.setEmail("test@test.com");
        user.setPassword("test!31");
        user.setAdmin(false);
        userRepository.save(user);

        mockMvc.perform(post("/api/session/" + session.getId() + "/participate/" + user.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user@test.com")
    void testUnParticipate() throws Exception {

        Session session = new Session();
        session.setName("Session");
        session.setDate(new Date());
        session.setDescription("Description");
        sessionRepository.save(session);
        session.setUsers(new ArrayList<>());

        User user = new User();
        user.setLastName("test");
        user.setFirstName("TEST");
        user.setEmail("test@test.com");
        user.setPassword("test!31");
        user.setAdmin(false);
        userRepository.save(user);
        session.getUsers().add(user);
        sessionRepository.save(session);

        mockMvc.perform(delete("/api/session/" + session.getId() + "/participate/" + user.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}