package com.ravvy.gymtrack.shared.util;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.Locale;
import java.util.Objects;

@Embeddable
@Getter
public class Email {

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String endereco;

    protected Email() {
    }

    public Email(String endereco) {

        if (endereco == null || endereco.isBlank()) {
            throw new IllegalArgumentException("E-mail é obrigatório");
        }

        final String emailNormalizado =
                endereco.trim().toLowerCase(Locale.ROOT);

        if (!emailNormalizado.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("E-mail inválido");
        }

        this.endereco = emailNormalizado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email email)) return false;
        return Objects.equals(endereco, email.endereco);
    }

    @Override
    public int hashCode() {
        return Objects.hash(endereco);
    }

    @Override
    public String toString() {
        return endereco;
    }
}