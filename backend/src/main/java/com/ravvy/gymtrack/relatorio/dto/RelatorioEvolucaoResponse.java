package com.ravvy.gymtrack.relatorio.dto;

import java.time.LocalDate;
import java.util.List;

public record RelatorioEvolucaoResponse(
        Long alunoId,
        String nomeAluno,
        LocalDate periodoInicial,
        LocalDate periodoFinal,
        List<EvolucaoAvaliacaoResponse> evolucao
) {
}