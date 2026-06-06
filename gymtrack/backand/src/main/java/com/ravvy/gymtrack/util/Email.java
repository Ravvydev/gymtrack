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
    private String email;

    @Column(length = 100,  nullable = false,  unique = true)
    @NotBlank
    private String senha;

    public Email(String email, String senha) {

        if(!validateEmail(email)) {
            throw new IllegalArgumentException("Email invalido");
        }

        if(!validateSenha(senha)) {
            throw new IllegalArgumentException("Senha invalida");
        }

        this.email = email;
        this.senha = senha;
    }

    private boolean validateEmail (String email) {
        return true;
    }

    private boolean validateSenha (String senha) {
        return true;
    }

}
