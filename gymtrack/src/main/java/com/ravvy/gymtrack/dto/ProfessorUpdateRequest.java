package com.ravvy.gymtrack.dto;

import com.ravvy.gymtrack.util.TipoSexoBiologico;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record ProfessorUpdateRequest(
        @NotBlank
        @Size(min = 3, max = 100)
        String nome,

        @NotNull
        @Past
        LocalDate dataNascimento,

        @NotNull
        TipoSexoBiologico sexo,

        @NotBlank
        @Pattern(regexp = "\\d{10,11}")
        String telefone,

        @NotNull
        Long enderecoId,

        @NotNull
        Long instituicaoId
) {
}
