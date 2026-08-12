package com.ravvy.gymtrack.util;

import lombok.Getter;

@Getter
public enum TipoSexoBiologico {

    MASCULINO("M"),
    FEMININO("F"),
    ;

    private final String abreviacao;

    TipoSexoBiologico(String abreviacao) {
        this.abreviacao = abreviacao;
    }
}
