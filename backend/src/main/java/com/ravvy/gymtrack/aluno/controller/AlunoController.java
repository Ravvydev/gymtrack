package com.ravvy.gymtrack.aluno.controller;

import com.ravvy.gymtrack.aluno.dto.AlunoCreateRequest;
import com.ravvy.gymtrack.aluno.dto.AlunoResponse;
import com.ravvy.gymtrack.aluno.dto.AlunoUpdateRequest;
import com.ravvy.gymtrack.aluno.service.AlunoService;
import com.ravvy.gymtrack.shared.senhas.dto.UpdateSenhaRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping
    public ResponseEntity<AlunoResponse> saveAluno(@RequestBody @Valid AlunoCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                alunoService.save(request)
        );
    }

    @PreAuthorize("hasRole('ADMIN') or @acesso.podeVerAluno(#id)")
    @GetMapping("/{id}")
    public ResponseEntity<AlunoResponse> getAluno(@PathVariable Long id) {

        return ResponseEntity.ok(
                alunoService.buscarResponsePorId(id)
        );

    }

    @PreAuthorize("hasRole('ADMIN') or @acesso.donoDoAluno(#id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAluno(@PathVariable Long id) {
        alunoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN') or @acesso.donoDoAluno(#id)")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAluno(@RequestBody @Valid AlunoUpdateRequest request,
                                            @PathVariable Long id) {

        alunoService.update(request, id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("@acesso.donoDoAluno(#id)")
    @PutMapping("/{id}/senha")
    public ResponseEntity<Void> updateSenhaAluno(@RequestBody @Valid UpdateSenhaRequest request,
                                                 @PathVariable Long id) {
        alunoService.updateSenha(request, id);
        return ResponseEntity.noContent().build();

    }

    // ===== AlunoController: novos endpoints (o aluno escolhe o próprio vínculo) =====

    @PutMapping("/{id}/professor/{idProfessor}")
    @PreAuthorize("@acesso.donoDoAluno(#id)")
    public ResponseEntity<Void> vincularProfessor(@PathVariable Long id,
                                                  @PathVariable Long idProfessor) {
        alunoService.vincularProfessor(id, idProfessor);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/professor")
    @PreAuthorize("@acesso.donoDoAluno(#id)")
    public ResponseEntity<Void> desvincularProfessor(@PathVariable Long id) {
        alunoService.desvincularProfessor(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/instituicao/{idInstituicao}")
    @PreAuthorize("@acesso.donoDoAluno(#id)")
    public ResponseEntity<Void> vincularInstituicao(@PathVariable Long id,
                                                    @PathVariable Long idInstituicao) {
        alunoService.vincularInstituicao(id, idInstituicao);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/instituicao")
    @PreAuthorize("@acesso.donoDoAluno(#id)")
    public ResponseEntity<Void> desvincularInstituicao(@PathVariable Long id) {
        alunoService.desvincularInstituicao(id);
        return ResponseEntity.noContent().build();
    }

}