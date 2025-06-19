package com.aston.task2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Email пользователя")
public class EmailDto {
    @NotBlank
    @Schema(description = "Email адрес", example = "user@example.com")
    private String email;
}
