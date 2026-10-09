package com.ravvy.gymtrack.testes.referencia;

import com.ravvy.gymtrack.avaliacao.enums.DirecaoResultado;
import com.ravvy.gymtrack.aluno.enums.TipoSexoBiologico;
import com.ravvy.gymtrack.avaliacao.enums.TipoTesteFisico;

public record ReferenciaSaude(

        int idade,

        TipoSexoBiologico sexo,

        TipoTesteFisico tipoTeste,

        DirecaoResultado direcao,

        double pontoCorte

) {
}