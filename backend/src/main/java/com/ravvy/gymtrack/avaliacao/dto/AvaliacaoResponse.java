package com.ravvy.gymtrack.avaliacao.dto;

import com.ravvy.gymtrack.testes.dto.ResultadoTesteResponse;
import com.ravvy.gymtrack.avaliacao.enums.TipoClassificacao;
import com.ravvy.gymtrack.aluno.enums.TipoSexoBiologico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.List;

public record AvaliacaoResponse(

        @NotNull
        Long id,

        @NotBlank
        String nomeAluno,

        @NotNull
        Integer idadeAluno,

        @NotNull
        TipoSexoBiologico sexoAluno,

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
        TipoClassificacao classificacaoImc,

        @NotNull
        @Positive
        Double rce,

        @NotNull
        TipoClassificacao classificacaoRce,

        @NotBlank
        String nomeProfessor,

        List<ResultadoTesteResponse> testesRealizados

) {
}