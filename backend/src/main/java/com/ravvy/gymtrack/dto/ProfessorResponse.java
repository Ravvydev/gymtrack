package com.ravvy.gymtrack.dto;

import com.ravvy.gymtrack.util.TipoSexoBiologico;
import jakarta.validation.constraints.NotBlank;

public record ProfessorResponse(
        @NotBlank Long id,
        @NotBlank String nome,
        @NotBlank Integer idade,
        @NotBlank String email,
        @NotBlank String telefone,
        @NotBlank TipoSexoBiologico sexo,
        @NotBlank String endereco,
        @NotBlank String instituicaoNome
) {
}
