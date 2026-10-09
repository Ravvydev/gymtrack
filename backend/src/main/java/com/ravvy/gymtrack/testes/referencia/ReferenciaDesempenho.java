package com.ravvy.gymtrack.testes.referencia;

import com.ravvy.gymtrack.avaliacao.enums.DirecaoResultado;
import com.ravvy.gymtrack.aluno.enums.TipoSexoBiologico;
import com.ravvy.gymtrack.avaliacao.enums.TipoTesteFisico;

public record ReferenciaDesempenho(

        int idade,

        TipoSexoBiologico sexo,

        TipoTesteFisico tipoTeste,

        DirecaoResultado direcao,

        double excelencia,

        double muitoBom,

        double bom,

        double razoavel

) implements ReferenciaTeste {
}