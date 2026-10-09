package com.ravvy.gymtrack.testes.referencia;

import com.ravvy.gymtrack.avaliacao.enums.TipoDesempenho;

public record FaixaDesempenho(
        Double limiteInferior,
        Double limiteSuperior,
        TipoDesempenho classificacao
) {
}