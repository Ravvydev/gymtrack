package com.ravvy.gymtrack.controller;

import com.ravvy.gymtrack.dto.AlunoCreateRequest;
import com.ravvy.gymtrack.dto.AlunoResponse;
import com.ravvy.gymtrack.dto.AlunoUpdateRequest;
import com.ravvy.gymtrack.dto.UpdateSenha;
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
    public void saveAluno(@RequestBody AlunoCreateRequest requestDTO) {
        alunoService.save(
                alunoService.toEntity(requestDTO)
        );
    }

    @GetMapping("/get/{id}")
    public AlunoResponse getAluno(@PathVariable Long id) {

        return alunoService.toResponse(id);

    }

    @DeleteMapping("/delete/{id}")
    public void deleteAluno(@PathVariable Long id) {
        alunoService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public void updateAluno(@PathVariable Long idAlunoExistente,
                            @RequestBody @Valid
                                    AlunoUpdateRequest alunoAtualizadoRequest) {

        alunoService.update(idAlunoExistente, alunoAtualizadoRequest);

    }

    @PutMapping("/update/{id}/senha")
    public void updateSenhaAluno(@RequestBody @Valid UpdateSenha request,
                                 @PathVariable Long id) {
        alunoService.updateSenha(request, id);
    }

}