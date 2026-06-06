package com.ravvy.gymtrack.dto;

import com.ravvy.gymtrack.util.TipoSexoBiologico;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record AlunoCreateRequest(
        @NotBlank String nome,
        @NotBlank LocalDate dataNascimento,
        @NotBlank TipoSexoBiologico sexo,
        @NotBlank String email,
        @NotBlank String senha,
        @NotBlank String telefone,
        @NotBlank String cpf,
        @NotBlank Long enderecoId,
        @NotBlank Long professorId,
        @NotBlank Long instituicaoId
) {
}
