package com.ravvy.gymtrack.dto;

import com.ravvy.gymtrack.util.Email;
import com.ravvy.gymtrack.util.Endereco;
import com.ravvy.gymtrack.util.Telefone;
import jakarta.validation.constraints.NotBlank;

public record InstituicaoResponse(

        @NotBlank Long id,
        @NotBlank String nome,
        @NotBlank String email,
        @NotBlank String telefone,
        @NotBlank String endereco
) {
}
