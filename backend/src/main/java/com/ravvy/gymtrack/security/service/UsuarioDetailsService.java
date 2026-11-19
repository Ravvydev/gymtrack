package com.ravvy.gymtrack.security.service;

import com.ravvy.gymtrack.security.autenticacao.UsuarioAutenticado;
import com.ravvy.gymtrack.shared.util.Email;
import com.ravvy.gymtrack.usuario.entity.Usuario;
import com.ravvy.gymtrack.usuario.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    private static final String CREDENCIAIS_INVALIDAS = "Credenciais inválidas";

    private final UsuarioRepository usuarioRepository;

    public UsuarioDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final Email email;
        try {
            // Normaliza (trim + minúsculas) e valida o formato.
            email = new Email(username);
        } catch (IllegalArgumentException e) {
            // E-mail malformado no login não pode virar erro 500.
            throw new UsernameNotFoundException(CREDENCIAIS_INVALIDAS);
        }

        Usuario usuario = usuarioRepository
                .findByEmailEndereco(email.getEndereco())
                .orElseThrow(() -> new UsernameNotFoundException(CREDENCIAIS_INVALIDAS));

        return new UsuarioAutenticado(usuario);
    }
}