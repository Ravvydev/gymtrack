package com.ravvy.gymtrack.aluno.dto;

import com.ravvy.gymtrack.aluno.enums.TipoSexoBiologico;
import com.ravvy.gymtrack.usuario.enums.PapelUsuario;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record AlunoResponse(
        @NotBlank Long id,
        @NotBlank String nome,
        @NotBlank LocalDate dataNascimento,
        @NotBlank TipoSexoBiologico sexo,
        @NotBlank String email,
        @NotBlank PapelUsuario papel,
        @NotBlank String telefone,
        @NotBlank String endereco,
        @NotBlank String instituicaoNome
) {
}