package com.ravvy.gymtrack.util;

import org.springframework.stereotype.Component;

@Component
public class CpfValidator {

    public boolean isValid(String cpf) {

        if (cpf == null) {
            return false;
        }

        // Remove pontos e traço, caso o CPF venha formatado
        cpf = cpf.replaceAll("\\D", "");

        // CPF precisa ter exatamente 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Rejeita CPFs com todos os números iguais
        if (cpf.chars().distinct().count() == 1) {
            return false;
        }

        // Validação do primeiro dígito
        int soma = 0;

        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }

        int resto = soma % 11;
        int primeiroDigito = resto < 2 ? 0 : 11 - resto;

        if (primeiroDigito != Character.getNumericValue(cpf.charAt(9))) {
            return false;
        }

        // Validação do segundo dígito
        soma = 0;

        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }

        resto = soma % 11;
        int segundoDigito = resto < 2 ? 0 : 11 - resto;

        return segundoDigito == Character.getNumericValue(cpf.charAt(10));
    }

}
