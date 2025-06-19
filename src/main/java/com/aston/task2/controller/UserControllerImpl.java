package com.aston.task2.controller;

import com.aston.task2.dto.EmailDto;
import com.aston.task2.dto.UserDto;
import com.aston.task2.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@Tag(name = "User API", description = "Операции с пользователями")
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserControllerImpl implements UserController{
    private final UserService userService;

    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createdUser = userService.save(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto userDto = userService.findById(id);
        return ResponseEntity.ok(userDto);
    }

    public ResponseEntity<UserDto> getUserByEmail(@Valid @RequestBody EmailDto emailDto) {
        UserDto userDto = userService.findByEmail(emailDto.getEmail());
        return ResponseEntity.ok(userDto);
    }

    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.update(id, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
