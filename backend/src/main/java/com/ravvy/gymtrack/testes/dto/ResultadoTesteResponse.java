package com.ravvy.gymtrack.testes.dto;

import com.ravvy.gymtrack.avaliacao.enums.TipoDesempenho;
import com.ravvy.gymtrack.avaliacao.enums.TipoSaude;
import com.ravvy.gymtrack.avaliacao.enums.TipoTesteFisico;

public record ResultadoTesteResponse(

        TipoTesteFisico tipoTeste,

        Double resultadoObtido,

        String unidadeMedida,

        TipoDesempenho desempenho,

        TipoSaude saude

) {
}