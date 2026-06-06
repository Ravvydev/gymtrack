package com.ravvy.gymtrack.dto;

import com.ravvy.gymtrack.util.TipoSexoBiologico;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record AvaliacaoUploadRequest(

        @NotNull
        Long alunoId,

        @NotNull
        LocalDate dataNascimentoAluno,

        @NotNull
        Long professorId,

        @NotNull
        TipoSexoBiologico sexo,

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
