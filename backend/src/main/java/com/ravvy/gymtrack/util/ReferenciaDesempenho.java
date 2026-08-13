package com.ravvy.gymtrack.util;

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