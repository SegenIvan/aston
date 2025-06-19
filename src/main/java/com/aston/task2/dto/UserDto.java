package com.aston.task2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@ToString
@Schema(description = "Данные пользователя")
public class UserDto {
    @Schema(description = "ID пользователя", example = "1")
    private Long id;

    @Schema(description = "Имя пользователя", example = "Иван")
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Schema(description = "Email пользователя", example = "user@example.com")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @Schema(description = "Возраст пользователя", example = "30")
    private Integer age;

    private LocalDateTime createdAt;
}
