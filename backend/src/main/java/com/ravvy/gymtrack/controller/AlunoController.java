package com.ravvy.gymtrack.controller;

import com.ravvy.gymtrack.dto.AlunoCreateRequest;
import com.ravvy.gymtrack.dto.AlunoResponse;
import com.ravvy.gymtrack.dto.AlunoUpdateRequest;
import com.ravvy.gymtrack.dto.UpdateSenhaRequest;
import com.ravvy.gymtrack.service.AlunoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{id}")
    public ResponseEntity<AlunoResponse> getAluno(@PathVariable Long id) {

        return ResponseEntity.ok(
                alunoService.buscarResponsePorId(id)
        );

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAluno(@PathVariable Long id) {
        alunoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAluno(@RequestBody @Valid AlunoUpdateRequest request,
                                            @PathVariable Long id) {

        alunoService.update(request, id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/senha")
    public ResponseEntity<Void> updateSenhaAluno(@RequestBody @Valid UpdateSenhaRequest request,
                                                 @PathVariable Long id) {
        alunoService.updateSenha(request, id);
        return ResponseEntity.noContent().build();

    }

}