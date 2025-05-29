package com.aston.task2.dto;

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
public class UserDto {
    private Long id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Email is mandatory")
    private String email;
    private Integer age;
    private LocalDateTime createdAt;
}
