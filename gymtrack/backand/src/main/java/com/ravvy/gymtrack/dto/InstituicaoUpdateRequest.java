package com.ravvy.gymtrack.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record InstituicaoUpdateRequest(

        @NotBlank
        @Size(min = 10 , max = 100)
        String nome,

        @NotNull
        Long enderecoId,

        @NotBlank
        @Pattern(regexp = "\\d{10,11}")
        String telefone

) {
}
