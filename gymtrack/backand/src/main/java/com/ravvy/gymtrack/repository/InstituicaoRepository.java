package com.ravvy.gymtrack.repository;

import com.ravvy.gymtrack.model.Aluno;
import com.ravvy.gymtrack.model.Instituicao;
import com.ravvy.gymtrack.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface InstituicaoRepository extends JpaRepository<Instituicao, Long> {

    List<Aluno> findAllTodosAlunos();

    List<Professor> findAllTodosProfessores();



}
