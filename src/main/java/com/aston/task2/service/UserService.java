package com.aston.task2.service;

import com.aston.task2.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto save(UserDto userDto);

    UserDto findById(Long id);

    List<UserDto> findAll();

    UserDto update(Long id, UserDto userDto);

    void delete(Long id);

    UserDto findByEmail(String email);
}
