package com.ravvy.gymtrack.testes.records;

import com.ravvy.gymtrack.avaliacao.enums.DirecaoResultado;
import com.ravvy.gymtrack.avaliacao.enums.TipoSexoBiologico;
import com.ravvy.gymtrack.avaliacao.enums.TipoTesteFisico;
import com.ravvy.gymtrack.testes.interfaces.ReferenciaTeste;

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