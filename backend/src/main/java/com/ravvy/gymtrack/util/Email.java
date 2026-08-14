package com.ravvy.gymtrack.util;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Email {

    @Column(name = "email", length = 100,  nullable = false,  unique = true)
    @NotBlank
    private String endereco;

    @Column(length = 100,  nullable = false,  unique = true)
    @NotBlank
    private String senha;

    public Email(String endereco, String senha) {

        if(!validateEmail(endereco)) {
            throw new IllegalArgumentException("Endereço de email invalido");
        }

        if(!validateSenha(senha)) {
            throw new IllegalArgumentException("Senha invalida");
        }

        this.endereco = endereco;
        this.senha = senha;
    }

    public Email() {

    }

    private boolean validateEmail (String endereco) {
        return true;
    }

    private boolean validateSenha (String senha) {
        return true;
    }

}
