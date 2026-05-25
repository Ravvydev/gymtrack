package com.ravvy.gymtrack.util;

import lombok.Getter;

@Getter
public class FaixaIMC {

    private Double maximo;

    public FaixaIMC(Double maximo) {
        this.maximo = maximo;
    }

}
