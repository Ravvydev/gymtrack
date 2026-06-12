package com.ravvy.gymtrack.controller;

import com.ravvy.gymtrack.dto.ProfessorCreateRequest;
import com.ravvy.gymtrack.dto.ProfessorResponse;
import com.ravvy.gymtrack.dto.ProfessorUpdateRequest;
import com.ravvy.gymtrack.dto.UpdateSenha;
import com.ravvy.gymtrack.model.Professor;
import com.ravvy.gymtrack.service.ProfessorService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/professor")
public class ProfessorController {

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @PostMapping
    public void saveProfessor(@RequestBody @Valid ProfessorCreateRequest request) {
        professorService.save(request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        professorService.deleteById(id);
    }

    @GetMapping("/{id}")
    public ProfessorResponse getProfessor(@PathVariable Long id) {
        Professor professor = professorService.buscarPorId(id);
        return professorService.toResponse(professor);
    }

    @PutMapping("/{id}")
    public ProfessorResponse update(@RequestBody @Valid ProfessorUpdateRequest request,
                                    @PathVariable Long id){
        return professorService.update(request, id);
    }

    @PutMapping("/{id}/senha")
    public void updateSenha(@RequestBody @Valid UpdateSenha request,
                            @PathVariable Long id) {
        professorService.updateSenha(request, id);
    }

    @PutMapping("/{idProfessor}/alunos/{idAluno}")
    public void vincularAluno(@PathVariable Long idProfessor,
                              @PathVariable Long idAluno) {
        professorService.vincularAluno(idProfessor, idAluno);
    }

}
