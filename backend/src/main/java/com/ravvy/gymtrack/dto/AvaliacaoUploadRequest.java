package com.ravvy.gymtrack.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record AvaliacaoUploadRequest(

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
        Double perimetroCintura,

        @NotEmpty
        List<@Valid ResultadoTesteRequest> testesRealizados

) {
}