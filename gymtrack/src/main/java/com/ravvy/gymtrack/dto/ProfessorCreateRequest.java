package com.ravvy.gymtrack.dto;

import com.ravvy.gymtrack.util.Telefone;
import com.ravvy.gymtrack.util.TipoSexoBiologico;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record ProfessorCreateRequest(
        @NotBlank String nome,

        @NotBlank LocalDate dataNascimento,

        @NotBlank TipoSexoBiologico sexo,

        @NotBlank String cpf,

        @NotBlank Telefone telefone,

        @NotBlank String email,

        @NotBlank String senha,

        @NotBlank Long enderecoId,

        @NotBlank Long instituicaoId
) {}
