package com.ravvy.gymtrack.shared.senhas.validation;

public final class SenhaValidator {

    private SenhaValidator() {
    }

    public static void validar(String senha) {

        if (senha == null || senha.isBlank()) {
            throw new IllegalArgumentException(
                    "Senha é obrigatória");
        }

        if (senha.length() < 8) {
            throw new IllegalArgumentException(
                    "Senha deve possuir pelo menos 8 caracteres");
        }

        if (!senha.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException(
                    "Senha deve possuir uma letra maiúscula");
        }

        if (!senha.matches(".*[a-z].*")) {
            throw new IllegalArgumentException(
                    "Senha deve possuir uma letra minúscula");
        }

        if (!senha.matches(".*\\d.*")) {
            throw new IllegalArgumentException(
                    "Senha deve possuir um número");
        }

        if (!senha.matches(".*[^A-Za-z0-9].*")) {
            throw new IllegalArgumentException(
                    "Senha deve possuir um caractere especial");
        }
    }
}