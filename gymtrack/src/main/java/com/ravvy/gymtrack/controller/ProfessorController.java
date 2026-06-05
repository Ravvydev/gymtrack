package com.ravvy.gymtrack.controller;

import com.ravvy.gymtrack.dto.ProfessorCreateRequest;
import com.ravvy.gymtrack.dto.ProfessorResponse;
import com.ravvy.gymtrack.dto.mapper.ProfessorMapper;
import com.ravvy.gymtrack.model.Professor;
import com.ravvy.gymtrack.service.ProfessorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/professor")
public class ProfessorController {

    private final ProfessorService professorService;
    private final ProfessorMapper professorMapper;

    public ProfessorController(ProfessorService professorService, ProfessorMapper professorMapper) {
        this.professorService = professorService;
        this.professorMapper = professorMapper;
    }

    @PostMapping
    public void saveProfessor(@RequestBody @Valid ProfessorCreateRequest request) {
        professorService.save(professorMapper.toEntity(request));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable @Valid Long id) {
        professorService.deleteById(id);
    }

    @GetMapping("/{id}")
    public ProfessorResponse getProfessor(@PathVariable @Valid Long id) {

        Professor professor = professorService.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Professor não encontrado no id " + id));

        return professorMapper.toResponse(professor);
    }

}
