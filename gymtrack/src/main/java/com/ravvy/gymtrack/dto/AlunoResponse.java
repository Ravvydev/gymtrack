package com.ravvy.gymtrack.dto;

import com.ravvy.gymtrack.util.TipoSexoBiologico;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record AlunoResponse(
        @NotBlank Long id,
        @NotBlank String nome,
        @NotBlank LocalDate dataNascimento,
        @NotBlank TipoSexoBiologico sexo,
        @NotBlank String email,
        @NotBlank String telefone,
        @NotBlank String enderecoLogradouro,
        @NotBlank String instituicaoNome
) {
}