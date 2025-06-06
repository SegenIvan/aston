package com.aston.task3;

import com.aston.task2.dao.UserDao;
import com.aston.task2.dao.UserDaoImpl;
import com.aston.task2.entity.User;
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
    void delete() {
        User user = new User("To Delete", "delete@test.com", 40);
        userDao.save(user);

        userDao.delete(user);

        assertNull(userDao.findById(user.getId()), "User should be deleted");
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
}
