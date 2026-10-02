package com.ravvy.gymtrack.testes.records;

import lombok.Getter;

@Getter
public class FaixaIMC {

    private Double maximo;

    public FaixaIMC(Double maximo) {
        this.maximo = maximo;
    }

}
