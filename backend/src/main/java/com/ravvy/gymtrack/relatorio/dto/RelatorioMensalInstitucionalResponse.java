package com.ravvy.gymtrack.relatorio.dto;

import com.ravvy.gymtrack.avaliacao.enums.TipoClassificacao;
import com.ravvy.gymtrack.relatorio.faixasEtaria.FaixaEtaria;

import java.time.YearMonth;
import java.util.List;

public record RelatorioMensalInstitucionalResponse(
        YearMonth mes,
        Integer quantidadeAlunos,

        Double imcMedio,

        Double rceMedio,
        TipoClassificacao classificacaoRce,

        List<FaixaEtaria> faixasEntarias


) {
}
