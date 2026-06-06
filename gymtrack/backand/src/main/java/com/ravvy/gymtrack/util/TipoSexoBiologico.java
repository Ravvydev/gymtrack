package com.ravvy.gymtrack.util;

import lombok.Getter;

@Getter
public enum TipoSexoBiologico {

    MASCULINO('M'),
    FEMININO('F');

    private char abreviacao;

    TipoSexoBiologico(char abreviacao) {
        this.abreviacao = abreviacao;
    }
}
