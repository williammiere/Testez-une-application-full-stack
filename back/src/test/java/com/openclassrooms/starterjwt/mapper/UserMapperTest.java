package com.openclassrooms.starterjwt.mapper;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.models.User;

public class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    public void setUp() {
        userMapper = Mappers.getMapper(UserMapper.class);
    }

    @Test
    public void testUserToUserDto() { // Should map user entity to user dto
        User user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");
        user.setLastName("TEST");
        user.setFirstName("test");
        user.setPassword("test!31");
        user.setAdmin(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        UserDto userDto = userMapper.toDto(user);

        assertNotNull(userDto);
        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getLastName(), userDto.getLastName());
        assertEquals(user.getFirstName(), userDto.getFirstName());
        assertEquals(user.getPassword(), userDto.getPassword());
        assertEquals(user.isAdmin(), userDto.isAdmin());
    }

    @Test
    public void testUserDtoToUser() { // Should map user dto to user entity

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail("test@test.com");
        userDto.setLastName("TEST");
        userDto.setFirstName("test");
        userDto.setPassword("test!31");
        userDto.setAdmin(true);

        User user = userMapper.toEntity(userDto);

        assertNotNull(user);
        assertEquals(userDto.getId(), user.getId());
        assertEquals(userDto.getEmail(), user.getEmail());
        assertEquals(userDto.getLastName(), user.getLastName());
        assertEquals(userDto.getFirstName(), user.getFirstName());
        assertEquals(userDto.getPassword(), user.getPassword());
        assertEquals(userDto.isAdmin(), user.isAdmin());
    }

    @Test
    public void testUserListToUserDtoList() { // Should map user list to user dto list

        User user = new User();
        user.setEmail("test@test.com");
        user.setLastName("TEST");
        user.setFirstName("test");
        user.setPassword("test!31");
        user.setAdmin(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setId(1L);

        User user2 = new User();
        user2.setEmail("test2@test.com");
        user2.setLastName("TEST2");
        user2.setFirstName("test2");
        user2.setPassword("test!32");
        user2.setAdmin(false);
        user2.setCreatedAt(LocalDateTime.now());
        user2.setUpdatedAt(LocalDateTime.now());
        user2.setId(2L);

        List<User> userList = Arrays.asList(user, user2);

        List<UserDto> userDtoList = userMapper.toDto(userList);

        assertNotNull(userDtoList);
        assertEquals(userList.size(), userDtoList.size());
        assertEquals(user.getId(), userDtoList.get(0).getId());
        assertEquals(user2.getId(), userDtoList.get(1).getId());
    }

    @Test
    public void testUserDtoListToUserList() { // Should map user dto list to user list

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail("test@test.com");
        userDto.setLastName("TEST");
        userDto.setFirstName("test");
        userDto.setPassword("test!31");
        userDto.setAdmin(true);

        UserDto userDto2 = new UserDto();
        userDto2.setId(2L);
        userDto2.setEmail("test2@test.com");
        userDto2.setLastName("TEST");
        userDto2.setFirstName("test");
        userDto2.setPassword("test!32");
        userDto2.setAdmin(false);

        List<UserDto> userDtoList = Arrays.asList(userDto, userDto2);

        List<User> userList = userMapper.toEntity(userDtoList);

        assertNotNull(userList);
        assertEquals(userDtoList.size(), userList.size());
        assertEquals(userDto.getId(), userList.get(0).getId());
        assertEquals(userDto2.getId(), userList.get(1).getId());
    }
}