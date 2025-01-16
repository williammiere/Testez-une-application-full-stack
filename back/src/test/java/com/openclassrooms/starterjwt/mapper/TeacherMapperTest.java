package com.openclassrooms.starterjwt.mapper;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.models.Teacher;

public class TeacherMapperTest {

    private TeacherMapper teacherMapper;

    @BeforeEach
    public void setUp() {
        teacherMapper = Mappers.getMapper(TeacherMapper.class);
    }

    @Test
    public void testTeacherToTeacherDto() { // Should map teacher entity to teacher dto

        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setLastName("DELAHAYE");
        teacher.setFirstName("Margot");
        teacher.setCreatedAt(LocalDateTime.now());
        teacher.setUpdatedAt(LocalDateTime.now());

        TeacherDto teacherDto = teacherMapper.toDto(teacher);

        assertNotNull(teacherDto);
        assertEquals(teacher.getId(), teacherDto.getId());
        assertEquals(teacher.getLastName(), teacherDto.getLastName());
        assertEquals(teacher.getFirstName(), teacherDto.getFirstName());
    }

    @Test
    public void testTeacherDtoToTeacher() { // Should map teacher dto to teacher entity
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(1L);
        teacherDto.setLastName("DELAHAYE");
        teacherDto.setFirstName("Margot");

        Teacher teacher = teacherMapper.toEntity(teacherDto);

        assertNotNull(teacher);
        assertEquals(teacherDto.getId(), teacher.getId());
        assertEquals(teacherDto.getLastName(), teacher.getLastName());
        assertEquals(teacherDto.getFirstName(), teacher.getFirstName());
    }

    @Test
    public void testTeacherListToTeacherDtoList() { // Should map teacher list to teacher dto list

        Teacher teacher = new Teacher();
                teacher.setId(1L);
                teacher.setLastName("DELAHAYE");
                teacher.setFirstName("Margot");
                teacher.setCreatedAt(LocalDateTime.now());
                teacher.setUpdatedAt(LocalDateTime.now());

        Teacher teacher2 = new Teacher();
        teacher2.setId(2L);
        teacher2.setLastName("THIERCELIN");
        teacher2.setFirstName("H├®l├¿ne");
        teacher2.setCreatedAt(LocalDateTime.now());
        teacher2.setUpdatedAt(LocalDateTime.now());

        List<Teacher> teacherList = Arrays.asList(teacher, teacher2);
        List<TeacherDto> teacherDtoList = teacherMapper.toDto(teacherList);

        assertNotNull(teacherDtoList);
        assertEquals(teacherList.size(), teacherDtoList.size());
        assertEquals(teacher.getId(), teacherDtoList.get(0).getId());
        assertEquals(teacher2.getId(), teacherDtoList.get(1).getId());
    }

    @Test
    public void testTeacherDtoListToTeacherList() { // Should map teacher dto list to teacher list

        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(1L);
        teacherDto.setLastName("DELAHAYE");
        teacherDto.setFirstName("Margot");

        TeacherDto teacherDto2 = new TeacherDto();
        teacherDto2.setId(2L);
        teacherDto2.setLastName("THIERCELIN");
        teacherDto2.setFirstName("H├®l├¿ne");

        List<TeacherDto> teacherDtoList = Arrays.asList(teacherDto, teacherDto2);
        List<Teacher> teacherList = teacherMapper.toEntity(teacherDtoList);

        assertNotNull(teacherList);
        assertEquals(teacherDtoList.size(), teacherList.size());
        assertEquals(teacherDto.getId(), teacherList.get(0).getId());
        assertEquals(teacherDto2.getId(), teacherList.get(1).getId());
    }
}
