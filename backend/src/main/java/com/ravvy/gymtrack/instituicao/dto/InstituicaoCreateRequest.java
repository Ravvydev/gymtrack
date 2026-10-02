package com.ravvy.gymtrack.instituicao.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record InstituicaoCreateRequest(

        @NotBlank
        String nome,

        @NotNull
        Long enderecoId,

        @NotBlank
        String telefone,

        @NotBlank
        @Email
        @Size(max = 100)
        String email,

        @NotBlank
        @Size(min = 8, max = 72)
        String senha

) {
}