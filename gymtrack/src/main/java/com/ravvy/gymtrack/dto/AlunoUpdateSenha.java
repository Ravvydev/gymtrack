package com.ravvy.gymtrack.dto;

import jakarta.validation.constraints.NotBlank;

public record AlunoUpdateSenha(
        @NotBlank String senhaAtual,
        @NotBlank String senhaNova
) {
}
