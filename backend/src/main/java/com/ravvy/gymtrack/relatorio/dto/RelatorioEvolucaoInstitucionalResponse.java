package com.ravvy.gymtrack.relatorio.dto;
import java.util.List;

public record RelatorioEvolucaoInstitucionalResponse(
        Long idInstituicao,
        String nomeInstituicao,
        Integer quantidadeTotalAlunos,

        List<RelatorioMensalInstitucionalResponse>  meses
) {
}
