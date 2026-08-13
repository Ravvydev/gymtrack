package com.ravvy.gymtrack.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record EnderecoCreateRequest(

        @NotBlank
        String rua,

        @NotBlank
        String bairro,

        @NotBlank
        String complemento,

        @NotBlank
        String cidade,

        @NotBlank
        String estado,

        @NotNull
        @Positive
        Integer numeroCasa,

        @NotBlank
        String cep,

        @NotBlank
        String uf

) {
}