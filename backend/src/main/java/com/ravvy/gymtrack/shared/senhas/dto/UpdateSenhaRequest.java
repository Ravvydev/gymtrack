package com.ravvy.gymtrack.shared.senhas.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateSenhaRequest(
        @NotBlank String senhaAtual,
        @NotBlank String senhaNova
) {
}
