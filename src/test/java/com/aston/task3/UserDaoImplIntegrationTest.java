package com.aston.task3;

import com.aston.task2.dao.UserDao;
import com.aston.task2.dao.UserDaoImpl;
import com.aston.task2.entity.User;
import com.aston.task2.exception.UserDaoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoImplIntegrationTest extends AbstractDaoIntegrationTest {

    private UserDao userDao;


    @BeforeEach
    void setUp() {
        userDao = new UserDaoImpl();
    }

    @Test
    void save() {
        User user = new User("Test User", "test@example.com", 30);
        userDao.save(user);

        assertNotNull(user.getId(), "User ID should be generated after save");
    }

    @Test
    void save_ShouldThrowException_WhenUserIsNull() {
        assertThrows(IllegalStateException.class,
                () -> userDao.save(null),
                "Should throw exception when user is null");
    }

    @Test
    void save_ShouldThrowException_WhenEmailIsNull() {
        User user = new User("Test", null, 30);
        assertThrows(IllegalStateException.class,
                () -> userDao.save(user),
                "Should throw exception when email is null");
    }

    @Test
    void save_ShouldThrowException_WhenEmailIsDuplicate() {
        User user1 = new User("User 1", "duplicate@test.com", 20);
        User user2 = new User("User 2", "duplicate@test.com", 25);

        userDao.save(user1);

        assertThrows(IllegalStateException.class,
                () -> userDao.save(user2),
                "Should throw exception for duplicate email");
    }

    @Test
    void findById() {
        User user = new User("Find User", "find@test.com", 25);
        userDao.save(user);

        User foundUser = userDao.findById(user.getId());

        assertAll(
                () -> assertNotNull(foundUser, "User should be found"),
                () -> assertEquals(user.getName(), foundUser.getName(), "Names should match"),
                () -> assertEquals(user.getEmail(), foundUser.getEmail(), "Emails should match")
        );
    }

    @Test
    void findById_ShouldReturnNull_WhenUserDoesNotExist() {
        User foundUser = userDao.findById(999L);
        assertNull(foundUser, "Should return null for non-existent user");
    }

    @Test
    void findById_ShouldThrowException_WhenIdIsNull() {
        assertThrows(UserDaoException.class,
                () -> userDao.findById(null),
                "Should throw exception when ID is null");
    }

    @Test
    void findAll() {
        userDao.save(new User("User 1", "user1@test.com", 20));
        userDao.save(new User("User 2", "user2@test.com", 25));

        List<User> users = userDao.findAll();

        assertEquals(2, users.size(), "Should return all users");
    }

    @Test
    void update() {
        User user = new User("Original", "original@test.com", 30);
        userDao.save(user);

        user.setName("Updated");
        user.setEmail("updated@test.com");
        user.setAge(35);
        userDao.update(user);

        User updatedUser = userDao.findById(user.getId());

        assertAll(
                () -> assertEquals("Updated", updatedUser.getName()),
                () -> assertEquals("updated@test.com", updatedUser.getEmail()),
                () -> assertEquals(35, updatedUser.getAge())
        );
    }

    @Test
    void update_ShouldThrowException_WhenUserNotExist() {
        User nonExistentUser = new User("Ghost", "ghost@test.com", 99);
        nonExistentUser.setId(999L);

        assertThrows(UserDaoException.class,
                () -> userDao.update(nonExistentUser),
                "Should throw exception when updating non-existent user");
    }

    @Test
    void update_ShouldThrowException_WhenUserIsNull() {
        assertThrows(IllegalStateException.class,
                () -> userDao.update(null),
                "Should throw exception when user is null");
    }

    @Test
    void delete() {
        User user = new User("To Delete", "delete@test.com", 40);
        userDao.save(user);

        userDao.delete(user);

        assertNull(userDao.findById(user.getId()), "User should be deleted");
    }

    @Test
    void delete_ShouldThrowException_WhenUserNotExist() {
        User nonExistentUser = new User("Ghost", "ghost@test.com", 99);
        nonExistentUser.setId(999L);

        assertThrows(UserDaoException.class,
                () -> userDao.delete(nonExistentUser),
                "Should throw exception when deleting non-existent user");
    }

    @Test
    void delete_ShouldThrowException_WhenUserIsNull() {
        assertThrows(IllegalStateException.class,
                () -> userDao.delete(null),
                "Should throw exception when user is null");
    }

    @Test
    void findByEmail() {
        User user1 = new User("User A", "a@test.com", 20);
        User user2 = new User("User B", "b@test.com", 25);
        userDao.save(user1);
        userDao.save(user2);

        User foundUser = userDao.findByEmail("b@test.com");

        assertEquals("User B", foundUser.getName(), "Should find user by email");
    }

    @Test
    void findByEmail_ShouldReturnNull_WhenEmailDoesNotExist() {
        User foundUser = userDao.findByEmail("nonexistent@test.com");
        assertNull(foundUser, "Should return null for non-existent email");
    }
}
