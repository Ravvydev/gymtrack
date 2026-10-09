package com.ravvy.gymtrack.relatorio.dto;

import java.time.LocalDate;

public record EvolucaoAvaliacaoResponse(
        LocalDate dataAvaliacao,
        Double peso,
        Double altura,
        Double imc,
        Double rce
) {
}