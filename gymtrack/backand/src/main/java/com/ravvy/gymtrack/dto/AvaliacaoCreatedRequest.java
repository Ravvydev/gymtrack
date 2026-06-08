package com.ravvy.gymtrack.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AvaliacaoCreatedRequest(

        @NotNull
        Long alunoId,

        @NotNull
        Long professorId,

        @NotNull
        @Positive
        Double peso,

        @NotNull
        @Positive
        Double altura,

        @NotNull
        @Positive
        Double envergadura,

        @NotNull
        @Positive
        Double perimetroCintura

) {
}
