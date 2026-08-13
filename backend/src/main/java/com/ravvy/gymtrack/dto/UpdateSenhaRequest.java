package com.ravvy.gymtrack.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateSenhaRequest(
        @NotBlank String senhaAtual,
        @NotBlank String senhaNova
) {
}
