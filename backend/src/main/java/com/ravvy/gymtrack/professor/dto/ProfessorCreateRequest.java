package com.ravvy.gymtrack.professor.dto;

import com.ravvy.gymtrack.shared.util.Telefone;
import com.ravvy.gymtrack.aluno.enums.TipoSexoBiologico;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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
        @Email
        @Size(max = 100)
        String email,

        @NotBlank
        @Size(min = 8, max = 72)
        String senha,

        @NotNull
        Long enderecoId,

        @NotNull
        Long instituicaoId

) {
}