package com.ravvy.gymtrack.shared.util;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.Objects;

@Embeddable
@Getter
public class Telefone {

    @Column(name = "telefone", nullable = false)
    private String numero;

    protected Telefone() {
    }

    public Telefone(String numero) {

        if (numero == null || numero.isBlank()) {
            throw new IllegalArgumentException("Telefone é obrigatório");
        }

        final String telefoneNormalizado =
                numero.replaceAll("\\D", "");

        if (telefoneNormalizado.length() != 11) {
            throw new IllegalArgumentException(
                    "Telefone deve conter 11 dígitos");
        }

        if (telefoneNormalizado.charAt(2) != '9') {
            throw new IllegalArgumentException(
                    "Telefone celular deve possuir o nono dígito");
        }

        this.numero = telefoneNormalizado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Telefone telefone)) return false;
        return Objects.equals(numero, telefone.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }

    @Override
    public String toString() {
        return numero;
    }
}