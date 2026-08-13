package com.ravvy.gymtrack.util;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

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

    public Telefone() {

    }

    private boolean validateTelephone(String numero) {

        if (numero == null) return false;

        // Retira todos os caracteres especiais e os substitui por uma string vazia
        String numeroFormatted = numero.replaceAll("[^0-9]", "");

        // Verifica se a string tem 10 ou 11 caracteres
        String regex = "\\d{10,11}";

        return numeroFormatted.matches(regex);
    }

}
