package com.aston.task2.dao;

import com.aston.task2.entity.User;

import java.util.List;

public interface UserDao {
    void save(User user);

    User findById(Long id);

    List<User> findAll();

    void update(User user);

    void delete(User user);

    User findByEmail(String email);
}
