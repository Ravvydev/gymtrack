package com.ravvy.gymtrack.usuario.repository;

import com.ravvy.gymtrack.shared.util.Email;
import com.ravvy.gymtrack.usuario.entity.Usuario;
import com.ravvy.gymtrack.usuario.enums.PapelUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmailEndereco(Email email);

    boolean existsByPapel(PapelUsuario papel);

    Optional<Usuario> findByEmailEndereco(String endereco);
}
