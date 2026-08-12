package com.ravvy.gymtrack.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InstituicaoCreateRequest(

        @NotBlank
        String nome,

        @NotNull
        Long enderecoId,

        @NotBlank
        String telefone,

        @NotBlank
        String email,

        @NotBlank
        String senha

) {
}