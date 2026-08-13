package com.ravvy.gymtrack.dto;

import com.ravvy.gymtrack.util.TipoSexoBiologico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record AlunoCreateRequest(

        @NotBlank
        String nome,

        @NotNull
        LocalDate dataNascimento,

        @NotNull
        TipoSexoBiologico sexo,

        @NotBlank
        String email,

        @NotBlank
        String senha,

        @NotBlank
        String telefone,

        @NotBlank
        @Pattern(
                regexp = "\\d{11}",
                message = "CPF deve conter 11 números"
        )
        String cpf,

        @NotNull
        Long enderecoId,

        @NotNull
        Long professorId,

        @NotNull
        Long instituicaoId

) {
}