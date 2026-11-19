package com.ravvy.gymtrack.security.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank @Size(max = 100) String email,
        @NotBlank @Size(max = 128) String senha
) {
}