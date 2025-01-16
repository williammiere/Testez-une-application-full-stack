package com.openclassrooms.starterjwt.mapper;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.TeacherService;
import com.openclassrooms.starterjwt.services.UserService;

@ExtendWith(MockitoExtension.class)
class SessionMapperTest {

    @Mock
    private TeacherService teacherService;

    @Mock
    private UserService userService;

    @InjectMocks
    private SessionMapperImpl sessionMapper;

    @BeforeEach
    public void setup() {
    }

    @Test
    void sessionMapper_toEntity() { // Should map teacher and user ids to session entity

        SessionDto dto = new SessionDto();
        dto.setTeacher_id(1L);
        dto.setUsers(Arrays.asList(2L, 3L));

        Teacher mockTeacher = new Teacher();
        mockTeacher.setId(1L);

        User user1 = new User();
        user1.setId(2L);

        User user2 = new User();
        user2.setId(3L);

        given(teacherService.findById(1L)).willReturn(mockTeacher);
        given(userService.findById(2L)).willReturn(user1);
        given(userService.findById(3L)).willReturn(user2);

        Session session = sessionMapper.toEntity(dto);

        assertThat(session.getTeacher()).isEqualTo(mockTeacher);
        assertThat(session.getUsers()).containsExactly(user1, user2);
    }

    @Test
    void sessionMapper_toDto() { // Should map teacher and user ids to session dto

        Teacher teacher = new Teacher();
        teacher.setId(1L);

        User user1 = new User();
        user1.setId(2L);

        User user2 = new User();
        user2.setId(3L);

        Session session = new Session();
        session.setTeacher(teacher);
        session.setUsers(Arrays.asList(user1, user2));

        SessionDto dto = sessionMapper.toDto(session);

        assertThat(dto.getTeacher_id()).isEqualTo(1L);
        assertThat(dto.getUsers()).containsExactly(2L, 3L);
    }

    @Test
    void sessionMapper_toDto_NullSession() { // Should return null when session dto is null

        Session session = null;
        SessionDto dto = sessionMapper.toDto(session);

        assertThat(dto).isNull();
    }

    @Test
    void sessionMapper_toDto_NullTeacher() { // Should return null when teacher dto is null
        Session session = new Session();
        session.setTeacher(null);

        SessionDto dto = sessionMapper.toDto(session);

        assertThat(dto.getTeacher_id()).isNull();
    }

    @Test
    void sessionMapper_toDto_NullSessionTeacherId() { // Should return null when teacher id is null
        Teacher teacher = new Teacher();
        teacher.setId(null);

        Session session = new Session();
        session.setTeacher(teacher);
        SessionDto dto = sessionMapper.toDto(session);

        assertThat(dto.getTeacher_id()).isNull();
    }
}