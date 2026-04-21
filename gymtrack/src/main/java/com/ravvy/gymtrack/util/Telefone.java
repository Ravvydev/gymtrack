package com.ravvy.gymtrack.util;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Getter
@Setter
public class Telefone {

    @Column(name = "telefone", nullable = false, unique = true)
    private String numero;

    public Telefone(String numero) {
        if (!validateTelephone(numero)) {
            throw new IllegalArgumentException("Telefone deve ter um numero valido");
        }
        this.numero = numero;
    }

    private boolean validateTelephone (String numero) {
        return false;
    }

}
