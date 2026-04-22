package com.ravvy.gymtrack.repository;

import com.ravvy.gymtrack.model.Aluno;
import com.ravvy.gymtrack.model.Instituicao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstituicaoRepository  extends JpaRepository<Instituicao, Long> {

    void adicionarAlunoEmInstituicao(Long id);

    List<Aluno> findAllTodosAlunos();

}
