package com.ravvy.gymtrack.instituicao.repository;

import com.ravvy.gymtrack.instituicao.entity.Instituicao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstituicaoRepository extends JpaRepository<Instituicao, Long> {

    boolean existsByIdAndUsuarioId(Long id, Long usuarioId);
    boolean existsByEnderecoId(Long enderecoId);

}
