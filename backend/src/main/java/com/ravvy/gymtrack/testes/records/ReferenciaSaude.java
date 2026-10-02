package com.ravvy.gymtrack.testes.records;

import com.ravvy.gymtrack.avaliacao.enums.DirecaoResultado;
import com.ravvy.gymtrack.avaliacao.enums.TipoSexoBiologico;
import com.ravvy.gymtrack.avaliacao.enums.TipoTesteFisico;

public record ReferenciaSaude(

        int idade,

        TipoSexoBiologico sexo,

        TipoTesteFisico tipoTeste,

        DirecaoResultado direcao,

        double pontoCorte

) {
}