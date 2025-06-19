package com.aston.task2.controller;

import com.aston.task2.dto.EmailDto;
import com.aston.task2.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserController {
    @Operation(summary = "Создать нового пользователя")
    @PostMapping("/")
    ResponseEntity<UserDto> createUser(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Данные нового пользователя",
            required = true)
            @Valid @RequestBody UserDto userDto);

    @Operation(summary = "Получить пользователя по ID")
    @GetMapping("/{id}")
    ResponseEntity<UserDto> getUserById(@Parameter(description = "ID пользователя") @PathVariable Long id);

    @Operation(summary = "Получить пользователя по email")
    @GetMapping("/getByEmail")
    ResponseEntity<UserDto> getUserByEmail(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Email пользователя",
            required = true)
            @Valid @RequestBody EmailDto emailDto);

    @Operation(summary = "Получить всех пользователей")
    @GetMapping("/")
    ResponseEntity<List<UserDto>> getAllUsers();

    @Operation(summary = "Обновить пользователя")
    @PutMapping("/{id}")
    ResponseEntity<UserDto> updateUser(@Parameter(description = "ID пользователя") @PathVariable Long id,
           @io.swagger.v3.oas.annotations.parameters.RequestBody(
             description = "Данные пользователя для обновления",
             required = true)
           @Valid @RequestBody UserDto userDto);

    @Operation(summary = "Удалить пользователя")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteUser(@Parameter(description = "ID пользователя") @PathVariable Long id);
}
