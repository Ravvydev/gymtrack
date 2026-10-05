package com.ravvy.gymtrack.shared.util;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.Objects;

@Embeddable
@Getter
public class Cpf {

    @Column(name = "cpf", length = 11, nullable = false, unique = true)
    private String numero;

    protected Cpf() {
    }

    public Cpf(String numero) {

        if (numero == null || numero.isBlank()) {
            throw new IllegalArgumentException("CPF é obrigatório");
        }

        final String cpfNormalizado = numero.replaceAll("\\D", "");

        if (!isValid(cpfNormalizado)) {
            throw new IllegalArgumentException("CPF inválido");
        }

        this.numero = cpfNormalizado;
    }

    private boolean isValid(String cpf) {

        if (cpf.length() != 11) {
            return false;
        }

        if (cpf.chars().distinct().count() == 1) {
            return false;
        }

        int somaPrimeiroDigito = 0;

        for (int i = 0; i < 9; i++) {
            somaPrimeiroDigito +=
                    Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }

        int restoPrimeiroDigito = somaPrimeiroDigito % 11;

        int primeiroDigito =
                restoPrimeiroDigito < 2
                        ? 0
                        : 11 - restoPrimeiroDigito;

        if (primeiroDigito !=
                Character.getNumericValue(cpf.charAt(9))) {
            return false;
        }

        int somaSegundoDigito = 0;

        for (int i = 0; i < 10; i++) {
            somaSegundoDigito +=
                    Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }

        int restoSegundoDigito = somaSegundoDigito % 11;

        int segundoDigito =
                restoSegundoDigito < 2
                        ? 0
                        : 11 - restoSegundoDigito;

        return segundoDigito ==
                Character.getNumericValue(cpf.charAt(10));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Cpf cpf)) {
            return false;
        }

        return Objects.equals(numero, cpf.numero);
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