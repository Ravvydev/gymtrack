package com.ravvy.gymtrack.util;

public record FaixaDesempenho(
        Double limiteInferior,
        Double limiteSuperior,
        TipoDesempenho classificacao
) {
}