package com.ravvy.gymtrack.professor.repository;

import com.ravvy.gymtrack.professor.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {

}
