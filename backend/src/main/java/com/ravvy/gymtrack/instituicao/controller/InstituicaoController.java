package com.ravvy.gymtrack.instituicao.controller;

import com.ravvy.gymtrack.aluno.dto.AlunoResponse;
import com.ravvy.gymtrack.instituicao.dto.InstituicaoCreateRequest;
import com.ravvy.gymtrack.instituicao.dto.InstituicaoResponse;
import com.ravvy.gymtrack.instituicao.dto.InstituicaoUpdateRequest;
import com.ravvy.gymtrack.instituicao.service.InstituicaoService;
import com.ravvy.gymtrack.professor.dto.ProfessorResponse;
import com.ravvy.gymtrack.shared.senhas.dto.UpdateSenhaRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<InstituicaoResponse> getInstituicao(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                instituicaoService.buscarResponsePorId(id)
        );
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<InstituicaoResponse>> listarAllInstituicoes() {
        return ResponseEntity.ok(
                instituicaoService.listarAllInstituicoes()
        );
    }

    @PreAuthorize("hasRole('ADMIN') or @acesso.donoDaInstituicao(#id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstituicao(
            @PathVariable Long id
    ) {
        instituicaoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN') or @acesso.donoDaInstituicao(#id)")
    @PutMapping("/{id}")
    public ResponseEntity<InstituicaoResponse> update(
            @RequestBody @Valid InstituicaoUpdateRequest request,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                instituicaoService.update(request, id)
        );
    }

    @PreAuthorize("@acesso.donoDaInstituicao(#id)")
    @PutMapping("/{id}/senha")
    public ResponseEntity<Void> updateSenha(
            @RequestBody @Valid UpdateSenhaRequest request,
            @PathVariable Long id
    ) {
        instituicaoService.updateSenha(request, id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN') or @acesso.donoDaInstituicao(#idInstituicao)")
    @GetMapping("/{idInstituicao}/alunos")
    public ResponseEntity<List<AlunoResponse>> getAlunos(
            @PathVariable Long idInstituicao
    ) {
        return ResponseEntity.ok(
                instituicaoService
                        .listarTodosAlunosInstituicao(idInstituicao)
        );
    }

    @PreAuthorize("hasRole('ADMIN') or @acesso.donoDaInstituicao(#idInstituicao)")
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