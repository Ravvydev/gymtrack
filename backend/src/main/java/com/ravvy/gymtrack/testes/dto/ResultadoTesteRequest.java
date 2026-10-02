package com.ravvy.gymtrack.testes.dto;

import com.ravvy.gymtrack.avaliacao.enums.TipoTesteFisico;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ResultadoTesteRequest(

        @NotNull
        TipoTesteFisico tipoTeste,

        @NotNull
        @Positive
        Double resultado

) {
}