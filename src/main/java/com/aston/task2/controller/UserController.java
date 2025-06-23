package com.aston.task2.controller;

import com.aston.task2.dto.EmailDto;
import com.aston.task2.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Создать нового пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Пользователь успешно создан",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(responseCode = "400", description = "Неверные входные данные"),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
            })
    @PostMapping("/")
    ResponseEntity<UserDto> createUser(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Данные нового пользователя",
            required = true)
            @Valid @RequestBody UserDto userDto);

    @Operation(summary = "Получить пользователя по ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь успешно найден",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(responseCode = "400", description = "Неверные входные данные"),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
            })
    @GetMapping("/{id}")
    ResponseEntity<UserDto> getUserById(@Parameter(description = "ID пользователя", example = "1", required = true)
                                        @PathVariable Long id);

    @Operation(summary = "Получить пользователя по email",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь успешно найден",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(responseCode = "400", description = "Неверные входные данные"),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
            })
    @GetMapping("/getByEmail")
    ResponseEntity<UserDto> getUserByEmail(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Email пользователя",
            required = true)
            @Valid @RequestBody EmailDto emailDto);

    @Operation(summary = "Получить всех пользователей",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список пользователей",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDto[].class)))
            })
    @GetMapping("/")
    ResponseEntity<List<UserDto>> getAllUsers();

    @Operation(summary = "Обновить пользователя",
            responses = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Пользователь успешно обновлен",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "400", description = "Неверные входные данные"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @PutMapping("/{id}")
    ResponseEntity<UserDto> updateUser(@Parameter(description = "ID пользователя") @PathVariable Long id,
           @io.swagger.v3.oas.annotations.parameters.RequestBody(
             description = "Данные пользователя для обновления",
             required = true)
           @Valid @RequestBody UserDto userDto);

    @Operation(summary = "Удалить пользователя", responses = {
            @ApiResponse(responseCode = "204", description = "Пользователь успешно удален"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteUser(@Parameter(description = "ID пользователя",
            example = "1", required = true) @PathVariable Long id);
}
