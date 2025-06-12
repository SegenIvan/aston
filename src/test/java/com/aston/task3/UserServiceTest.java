package com.aston.task3;

import com.aston.task2.dao.UserDaoImpl;
import com.aston.task2.entity.User;
import com.aston.task2.exception.UserDaoException;
import com.aston.task2.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private UserServiceImpl userService;

    @Mock
    private UserDaoImpl userDaoImpl;

    private User testUser;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userDaoImpl);
        testUser = new User("Test User", "test@example.com", 30);
        testUser.setId(1L);
        testUser.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void createUser() {
        when(userDaoImpl.save(any(User.class))).thenReturn(testUser);

        User createdUser = userService.save(new User());

        assertNotNull(createdUser.getId());
        assertEquals("Test User", createdUser.getName());
        assertEquals("test@example.com", createdUser.getEmail());
        assertEquals(30, createdUser.getAge());

        verify(userDaoImpl, times(1)).save(any(User.class));
        verifyNoMoreInteractions(userDaoImpl);
    }

    @Test
    void create_ShouldThrowException_WhenDaoThrowsException() {
        User user = new User("Name", "valid@email.com", 30);
        when(userDaoImpl.save(any(User.class))).thenThrow(new UserDaoException("DB error"));

        assertThrows(UserDaoException.class,
                () -> userService.save(user),
                "Should propagate DataAccessException from DAO");
    }

    @Test
    void getUserById() {
        User expectedUser = new User("Test User", "test@example.com", 30);
        expectedUser.setId(1L);
        when(userDaoImpl.findById(1L)).thenReturn(expectedUser);

        User actualUser = userService.findById(1L);

        assertEquals(expectedUser, actualUser);
        verify(userDaoImpl, times(1)).findById(1L);
    }

    @Test
    void findById_ShouldThrowException_WhenDaoThrowsException() {
        when(userDaoImpl.findById(anyLong())).thenThrow(new UserDaoException("DB error"));

        assertThrows(UserDaoException.class,
                () -> userService.findById(1L),
                "Should propagate DataAccessException from DAO");
    }

    @Test
    void getAllUsers() {
        User user1 = new User("User 1", "user1@test.com", 25);
        User user2 = new User("User 2", "user2@test.com", 30);
        List<User> expectedUsers = Arrays.asList(user1, user2);

        when(userDaoImpl.findAll()).thenReturn(expectedUsers);

        List<User> actualUsers = userService.findAll();

        assertEquals(2, actualUsers.size());
        verify(userDaoImpl, times(1)).findAll();
    }

    @Test
    void updateUser() {
        userService.update(testUser);

        verify(userDaoImpl, times(1)).update(testUser);
    }

    @Test
    void deleteUser() {
        User existingUser = new User("To Delete", "delete@test.com", 40);
        existingUser.setId(1L);

        doNothing().when(userDaoImpl).delete(existingUser);

        userService.delete(existingUser);

        verify(userDaoImpl, times(1)).delete(existingUser);
    }

    @Test
    void delete_ShouldThrowException_WhenDaoThrowsException() {
        doThrow(new UserDaoException("DB error")).when(userDaoImpl).delete(any(User.class));

        assertThrows(UserDaoException.class,
                () -> userService.delete(testUser),
                "Should propagate DataAccessException from DAO");
    }

    @Test
    void getUserByEmail() {
        User expectedUser = new User("Email User", "email@test.com", 35);
        expectedUser.setId(2L);

        when(userDaoImpl.findByEmail("email@test.com")).thenReturn(expectedUser);

        User actualUser = userService.findByEmail("email@test.com");

        assertEquals(expectedUser, actualUser);
        verify(userDaoImpl, times(1)).findByEmail("email@test.com");
    }
}
