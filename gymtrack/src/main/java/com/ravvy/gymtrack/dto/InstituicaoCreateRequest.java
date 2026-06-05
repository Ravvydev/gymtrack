package com.ravvy.gymtrack.dto;

import jakarta.validation.constraints.NotBlank;

public record InstituicaoCreateRequest(

        @NotBlank String nome,
        @NotBlank Long enderecoId,
        @NotBlank String telefone,
        @NotBlank String email,
        @NotBlank String senha

){
}
