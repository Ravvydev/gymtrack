package com.ravvy.gymtrack.dto;

import jakarta.validation.constraints.NotBlank;

public record InstituicaoResponse(

        @NotBlank Long id,
        @NotBlank String nome,
        @NotBlank String email,
        @NotBlank String telefone,
        @NotBlank String endereco
) {
}
