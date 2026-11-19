package com.ravvy.gymtrack.professor.dto;

import com.ravvy.gymtrack.aluno.enums.TipoSexoBiologico;
import com.ravvy.gymtrack.usuario.enums.PapelUsuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProfessorResponse(
        @NotBlank Long id,
        @NotBlank String nome,
        @NotBlank Integer idade,
        @NotBlank String email,
        @NotBlank String telefone,
        @NotBlank TipoSexoBiologico sexo,
        @NotBlank String endereco,
        @NotBlank String instituicaoNome,
        @NotNull PapelUsuario papel
) {
}
