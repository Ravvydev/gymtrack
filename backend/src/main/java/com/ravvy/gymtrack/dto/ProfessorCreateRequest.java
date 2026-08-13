package com.ravvy.gymtrack.dto;

import com.ravvy.gymtrack.util.Telefone;
import com.ravvy.gymtrack.util.TipoSexoBiologico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ProfessorCreateRequest(

        @NotBlank
        String nome,

        @NotNull
        LocalDate dataNascimento,

        @NotNull
        TipoSexoBiologico sexo,

        @NotBlank
        String cpf,

        @NotNull
        Telefone telefone,

        @NotBlank
        String email,

        @NotBlank
        String senha,

        @NotNull
        Long enderecoId,

        @NotNull
        Long instituicaoId

) {
}