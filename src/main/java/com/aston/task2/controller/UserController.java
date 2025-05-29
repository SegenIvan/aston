package com.aston.task2.controller;

import com.aston.task2.dto.EmailDto;
import com.aston.task2.dto.UserDto;
import com.aston.task2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createdUser = userService.save(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto userDto = userService.findById(id);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/getByEmail")
    public ResponseEntity<UserDto> getUserByEmail(@Valid @RequestBody EmailDto emailDto) {
        UserDto userDto = userService.findByEmail(emailDto.getEmail());
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.update(id, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
