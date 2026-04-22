package com.ravvy.gymtrack.service;

import com.ravvy.gymtrack.repository.ProfessorRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfessorService {

    private ProfessorRepository professorRepository;

    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

}