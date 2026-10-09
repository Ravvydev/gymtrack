package com.ravvy.gymtrack.relatorio.faixasEtaria;

import com.ravvy.gymtrack.avaliacao.enums.TipoClassificacao;

public record FaixaEtaria(

        Integer idadeMinima,
        Integer idadeMaxima,
        Double percentualAlunos,
        Integer quantidadeAlunos,

        Double imcMedio,
        TipoClassificacao classificacaoImc,
        Integer quantidadeAlunosSaudaveisImc,
        Integer quantidadeAlunosRiscoImc,

        Double rceMedio,
        TipoClassificacao classificacaoRce,
        Integer quantidadeAlunosSaudaveisRce,
        Integer quantidadeAlunosRiscoRce

) {
}
