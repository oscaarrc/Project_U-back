package com.edatatest.RestAPI.services;

import com.edatatest.RestAPI.dto.UserDto;
import com.edatatest.RestAPI.entities.User;
import com.edatatest.RestAPI.exceptions.UserNotFoundException;
import com.edatatest.RestAPI.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void findAll() {

        // Arrange
        List<User> userList = new ArrayList<>();

        User user1 = new User();
        user1.setId(1L);
        user1.setName("User 1");

        User user2 = new User();
        user2.setId(2L);
        user2.setName("User 2");

        userList.add(user1);
        userList.add(user2);
        when(userRepository.findAll(Sort.unsorted())).thenReturn(userList);

        // Act
        List<UserDto> result = userService.findAll(Sort.unsorted());

        // Assert
        assertEquals(userList.size(), result.size());
        assertEquals(userList.get(0).getId(), result.get(0).getId());
        assertEquals(userList.get(0).getName(), result.get(0).getName());
        assertEquals(userList.get(1).getId(), result.get(1).getId());
        assertEquals(userList.get(1).getName(), result.get(1).getName());
    }

    @Test
    void findUserById() {

        // Arrange
        User user = new User();
        user.setId(1L);
        user.setName("User 1");
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        // Act
        UserDto result = userService.findUserById(user.getId());

        // Assert
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
    }

    @Test
    void testFindUserById_UserNotFound() {

        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Assert
        assertThrows(UserNotFoundException.class, () -> userService.findUserById(userId));
    }
}
