package com.ravvy.gymtrack.instituicao.dto;

import com.ravvy.gymtrack.usuario.enums.PapelUsuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InstituicaoResponse(
        @NotBlank Long id,
        @NotBlank String nome,
        @NotBlank String email,
        @NotBlank String telefone,
        @NotBlank String endereco,
        @NotNull PapelUsuario papel
        ) {
}
