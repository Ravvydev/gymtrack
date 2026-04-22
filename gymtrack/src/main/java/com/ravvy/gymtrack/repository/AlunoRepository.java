package com.ravvy.gymtrack.repository;

import com.ravvy.gymtrack.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

}
