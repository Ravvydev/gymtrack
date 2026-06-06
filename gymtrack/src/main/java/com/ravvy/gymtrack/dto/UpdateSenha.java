package com.ravvy.gymtrack.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateSenha(
        @NotBlank String senhaAtual,
        @NotBlank String senhaNova
) {
}
