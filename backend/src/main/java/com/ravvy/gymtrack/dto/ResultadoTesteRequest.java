package com.ravvy.gymtrack.dto;

import com.ravvy.gymtrack.util.TipoTesteFisico;
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