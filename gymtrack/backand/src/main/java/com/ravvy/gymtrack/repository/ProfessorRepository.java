package com.ravvy.gymtrack.repository;

import com.ravvy.gymtrack.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    void adicionarProfessorInstituicao(Long idInstituicao);


}
