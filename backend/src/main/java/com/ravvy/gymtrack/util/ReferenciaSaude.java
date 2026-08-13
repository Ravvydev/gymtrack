package com.ravvy.gymtrack.util;

public record ReferenciaSaude(

        int idade,

        TipoSexoBiologico sexo,

        TipoTesteFisico tipoTeste,

        DirecaoResultado direcao,

        double pontoCorte

) {
}