package com.ravvy.gymtrack.aluno.repository;

import com.ravvy.gymtrack.aluno.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    boolean existsByIdAndUsuarioId(Long id, Long usuarioId);
    boolean existsByIdAndProfessorUsuarioId(Long id, Long usuarioId);
    boolean existsByIdAndInstituicaoUsuarioId(Long id, Long usuarioId);
    boolean existsByEnderecoId(Long enderecoId);

}
