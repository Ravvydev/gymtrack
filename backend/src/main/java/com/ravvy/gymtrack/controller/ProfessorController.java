package com.ravvy.gymtrack.controller;

import com.ravvy.gymtrack.dto.*;
import com.ravvy.gymtrack.service.ProfessorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/professor")
public class ProfessorController {

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @PostMapping
    public ResponseEntity<ProfessorResponse> saveProfessor(@RequestBody @Valid ProfessorCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                professorService.save(request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        professorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponse> getProfessor(@PathVariable Long id) {
        return ResponseEntity.ok(
                professorService.buscarResponsePorId(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorResponse> update(@RequestBody @Valid ProfessorUpdateRequest request,
                                                    @PathVariable Long id) {
        return ResponseEntity.ok(
                professorService.update(request, id)
        );
    }

    @PutMapping("/{id}/senha")
    public ResponseEntity<UpdateSenhaResponse> updateSenha(@RequestBody @Valid UpdateSenhaRequest request,
                                                           @PathVariable Long id) {
        return ResponseEntity.ok(
                professorService.updateSenha(request, id)
        );
    }

    @PutMapping("/{idProfessor}/alunos/{idAluno}")
    public ResponseEntity<Void> vincularAluno(@PathVariable Long idProfessor,
                                              @PathVariable Long idAluno) {
        professorService.vincularAluno(idProfessor, idAluno);
        return ResponseEntity.noContent().build();
    }

}
