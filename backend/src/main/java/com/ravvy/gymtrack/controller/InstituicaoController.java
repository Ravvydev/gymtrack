package com.ravvy.gymtrack.controller;

import com.ravvy.gymtrack.dto.*;
import com.ravvy.gymtrack.service.InstituicaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instituicao")
public class InstituicaoController {

    private final InstituicaoService instituicaoService;

    public InstituicaoController(
            InstituicaoService instituicaoService
    ) {
        this.instituicaoService = instituicaoService;
    }

    @PostMapping
    public ResponseEntity<InstituicaoResponse> saveInstituicao(
            @RequestBody @Valid InstituicaoCreateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        instituicaoService.save(request)
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstituicaoResponse> getInstituicao(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                instituicaoService.buscarResponsePorId(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<InstituicaoResponse>> listarAllInstituicoes() {
        return ResponseEntity.ok(
                instituicaoService.listarAllInstituicoes()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstituicao(
            @PathVariable Long id
    ) {
        instituicaoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstituicaoResponse> update(
            @RequestBody @Valid InstituicaoUpdateRequest request,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                instituicaoService.update(request, id)
        );
    }

    @PutMapping("/{id}/senha")
    public ResponseEntity<Void> updateSenha(
            @RequestBody @Valid UpdateSenhaRequest request,
            @PathVariable Long id
    ) {
        instituicaoService.updateSenha(request, id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{idInstituicao}/alunos/{idAluno}")
    public ResponseEntity<Void> vincularAluno(
            @PathVariable Long idInstituicao,
            @PathVariable Long idAluno
    ) {
        instituicaoService.vincularAlunoInstituicao(
                idInstituicao,
                idAluno
        );
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{idInstituicao}/professores/{idProfessor}")
    public ResponseEntity<Void> vincularProfessor(
            @PathVariable Long idInstituicao,
            @PathVariable Long idProfessor
    ) {
        instituicaoService.vincularProfessorInstituicao(
                idInstituicao,
                idProfessor
        );
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{idInstituicao}/alunos")
    public ResponseEntity<List<AlunoResponse>> getAlunos(
            @PathVariable Long idInstituicao
    ) {
        return ResponseEntity.ok(
                instituicaoService
                        .listarTodosAlunosInstituicao(idInstituicao)
        );
    }

    @GetMapping("/{idInstituicao}/professores")
    public ResponseEntity<List<ProfessorResponse>> getProfessores(
            @PathVariable Long idInstituicao
    ) {
        return ResponseEntity.ok(
                instituicaoService
                        .listarTodosProfessoresInstituicao(idInstituicao)
        );
    }
}