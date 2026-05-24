package com.ravvy.gymtrack.controller;

import com.ravvy.gymtrack.dto.ProfessorRequestDTO;
import com.ravvy.gymtrack.dto.ProfessorResponseDTO;
import com.ravvy.gymtrack.dto.mapper.MapperProfessor;
import com.ravvy.gymtrack.model.Professor;
import com.ravvy.gymtrack.service.ProfessorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/professor")
public class ProfessorController {

    private ProfessorService professorService;
    private MapperProfessor mapperProfessor;

    public ProfessorController(ProfessorService professorService, MapperProfessor mapperProfessor) {
        this.professorService = professorService;
        this.mapperProfessor = mapperProfessor;
    }

    @PostMapping
    public void saveProfessor(@RequestBody @Valid ProfessorRequestDTO request) {
        professorService.save(mapperProfessor.requestToEntity(request));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable @Valid Long id) {
        Professor professor = professorService.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Professor não encontrado no id" + id));
        professorService.delete(professor);
    }

    @GetMapping("/{id}")
    public ProfessorResponseDTO getProfessor(@PathVariable @Valid Long id) {

        Professor professor = professorService.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Professor não encontrado no id " + id));

        return mapperProfessor.entityToResponseDTO(professor);
    }

}
