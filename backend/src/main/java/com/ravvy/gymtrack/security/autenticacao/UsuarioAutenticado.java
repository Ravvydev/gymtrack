package com.ravvy.gymtrack.security.autenticacao; // ajuste para o seu pacote

import com.ravvy.gymtrack.usuario.entity.Usuario;
import com.ravvy.gymtrack.usuario.enums.PapelUsuario;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UsuarioAutenticado implements UserDetails {

    @Getter
    private final Long id;
    private final String email;
    private final String senhaHash;
    @Getter
    private final PapelUsuario papel;

    public UsuarioAutenticado(Usuario usuario) {
        this.id = usuario.getId();
        this.email = usuario.getEmail().getEndereco();
        this.senhaHash = usuario.getSenhaHash();
        this.papel = usuario.getPapel();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // O prefixo ROLE_ é o que faz hasRole("ALUNO") funcionar.
        return List.of(new SimpleGrantedAuthority("ROLE_" + papel.name()));
    }

    @Override
    public String getPassword() {
        return senhaHash;
    }

    @Override
    public String getUsername() {
        return email;
    }
}