package com.ravvy.gymtrack.testes.records;

import com.ravvy.gymtrack.avaliacao.enums.TipoDesempenho;

public record FaixaDesempenho(
        Double limiteInferior,
        Double limiteSuperior,
        TipoDesempenho classificacao
) {
}