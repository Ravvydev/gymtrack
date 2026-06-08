package com.ravvy.gymtrack.dto;

import com.ravvy.gymtrack.util.TipoClassificacao;
import com.ravvy.gymtrack.util.TipoSexoBiologico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record AvaliacaoResponse(

        @NotBlank
        String nomeAluno,

        @NotNull
        Integer idadeAluno,

        @NotNull
        TipoSexoBiologico sexoAluno,

        @NotNull
        TipoClassificacao zona,

        @NotNull
        LocalDate dataAvaliacao,

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

        @NotNull
        @Positive
        Double imc,

        @NotNull
        @Positive
        Double rce,

        @NotBlank
        String nomeProfessor

) {
}
