package com.ravvy.gymtrack.controller;

import com.ravvy.gymtrack.dto.AlunoCreateRequest;
import com.ravvy.gymtrack.dto.AlunoResponse;
import com.ravvy.gymtrack.dto.AlunoUpdateRequest;
import com.ravvy.gymtrack.dto.UpdateSenha;
import com.ravvy.gymtrack.model.Aluno;
import com.ravvy.gymtrack.service.AlunoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping
    public AlunoResponse saveAluno(@RequestBody AlunoCreateRequest request) {
        return alunoService.save(request);
    }

    @GetMapping("/{id}")
    public AlunoResponse getAluno(@PathVariable Long id) {

        Aluno aluno = alunoService.buscarPorId(id);
        return alunoService.toResponse(aluno);

    }

    @DeleteMapping("/{id}")
    public void deleteAluno(@PathVariable Long id) {
        alunoService.deleteById(id);
    }

    @PutMapping("/{id}")
    public void updateAluno(@RequestBody @Valid AlunoUpdateRequest request,
                            @PathVariable Long id) {

        alunoService.update(request, id);

    }

    @PutMapping("/{id}/senha")
    public void updateSenhaAluno(@RequestBody @Valid UpdateSenha request,
                                 @PathVariable Long id) {
        alunoService.updateSenha(request, id);
    }

}