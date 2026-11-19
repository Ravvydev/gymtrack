package com.ravvy.gymtrack.usuario.entity;

import com.ravvy.gymtrack.shared.util.Email;
import com.ravvy.gymtrack.usuario.enums.PapelUsuario;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "usuario")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "papel", nullable = false, length = 20)
    private PapelUsuario papel;

    @Embedded
    private Email email;

    @Column(name = "senha_hash", nullable = false, length = 60)
    private String senhaHash;

    public Usuario(Email email, String senhaHash, PapelUsuario papel) {
        this.email = Objects.requireNonNull(email, "E-mail é obrigatório");
        this.senhaHash = Objects.requireNonNull(senhaHash, "Hash de senha é obrigatório");
        this.papel = Objects.requireNonNull(papel, "Papel é obrigatório");
    }

    public void alterarSenha(String hashNovo) {
        this.senhaHash = hashNovo;
    }
}
