package com.ravvy.gymtrack.controller;

import com.ravvy.gymtrack.dto.InstituicaoCreateRequest;
import com.ravvy.gymtrack.dto.InstituicaoResponse;
import com.ravvy.gymtrack.dto.InstituicaoUpdateRequest;
import com.ravvy.gymtrack.dto.UpdateSenha;
import com.ravvy.gymtrack.model.Aluno;
import com.ravvy.gymtrack.model.Instituicao;
import com.ravvy.gymtrack.model.Professor;
import com.ravvy.gymtrack.service.InstituicaoService;
import jakarta.validation.Valid;
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
    public InstituicaoResponse saveInstituicao(
            @RequestBody @Valid InstituicaoCreateRequest request
    ) {
        return instituicaoService.save(request);
    }

    @GetMapping("/{id}")
    public InstituicaoResponse getInstituicao(
            @PathVariable Long id
    ) {
        Instituicao instituicao =
                instituicaoService.buscarPorId(id);

        return instituicaoService.toResponse(instituicao);
    }

    @GetMapping
    public List<Instituicao> listarAllInstituicoes() {
        return instituicaoService.listarAllInstituicoes();
    }

    @DeleteMapping("/{id}")
    public void deleteInstituicao(
            @PathVariable Long id
    ) {
        instituicaoService.deleteById(id);
    }

    @PutMapping("/{id}")
    public InstituicaoResponse update(
            @RequestBody @Valid InstituicaoUpdateRequest request,
            @PathVariable Long id
    ) {
        return instituicaoService.update(request, id);
    }

    @PutMapping("/{id}/senha")
    public void updateSenha(
            @RequestBody @Valid UpdateSenha request,
            @PathVariable Long id
    ) {
        instituicaoService.updateSenha(request, id);
    }

    @PutMapping("/{idInstituicao}/alunos/{idAluno}")
    public void vincularAluno(
            @PathVariable Long idInstituicao,
            @PathVariable Long idAluno
    ) {
        instituicaoService.vincularAlunoInstituicao(
                idInstituicao,
                idAluno
        );
    }

    @PutMapping("/{idInstituicao}/professores/{idProfessor}")
    public void vincularProfessor(
            @PathVariable Long idInstituicao,
            @PathVariable Long idProfessor
    ) {
        instituicaoService.vincularProfessorInstituicao(
                idInstituicao,
                idProfessor
        );
    }

    @GetMapping("/{idInstituicao}/alunos")
    public List<Aluno> getAlunos(
            @PathVariable Long idInstituicao
    ) {
        return instituicaoService
                .listarTodosAlunosInstituicao(idInstituicao);
    }

    @GetMapping("/{idInstituicao}/professores")
    public List<Professor> getProfessores(
            @PathVariable Long idInstituicao
    ) {
        return instituicaoService
                .listarTodosProfessoresInstituicao(idInstituicao);
    }
}