package com.ravvy.gymtrack.dto;

import com.ravvy.gymtrack.util.TipoDesempenho;
import com.ravvy.gymtrack.util.TipoSaude;
import com.ravvy.gymtrack.util.TipoTesteFisico;

public record ResultadoTesteResponse(

        TipoTesteFisico tipoTeste,

        Double resultadoObtido,

        String unidadeMedida,

        TipoDesempenho desempenho,

        TipoSaude saude

) {
}