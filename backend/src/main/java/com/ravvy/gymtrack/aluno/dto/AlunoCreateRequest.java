package com.ravvy.gymtrack.aluno.dto;

import com.ravvy.gymtrack.aluno.enums.TipoSexoBiologico;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AlunoCreateRequest(

        @NotBlank
        String nome,

        @NotNull
        LocalDate dataNascimento,

        @NotNull
        TipoSexoBiologico sexo,

        @NotBlank
        @Email
        @Size(max = 100)
        String email,

        @NotBlank
        @Size(min = 8, max = 72)
        String senha,

        @NotBlank
        String telefone,

        @NotBlank
        String cpf,

        @NotNull
        Long enderecoId,

        @NotNull
        Long professorId,

        @NotNull
        Long instituicaoId

) {
}