package com.ravvy.gymtrack.util;

public enum TipoSexo {

    MASCULINO('M'),
    FEMININO('F');

    private char abreviacao;

    TipoSexo(char abreviacao) {
        this.abreviacao = abreviacao;
    }
}
