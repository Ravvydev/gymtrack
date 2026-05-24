package com.ravvy.gymtrack.service;

import com.ravvy.gymtrack.model.Professor;
import com.ravvy.gymtrack.repository.ProfessorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class ProfessorService {

    private ProfessorRepository professorRepository;

    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public void save(Professor professor) {

        if (professorRepository.existsById(professor.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Professor ja existe");
        }

        professorRepository.save(professor);
    }

    public void delete(Professor professor) {
        if (!professorRepository.existsById(professor.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Professor não existe");
        }
        professorRepository.delete(professor);
    }

    public Optional<Professor> findById(long id) {
        return professorRepository.findById(id);
    }

}