package com.ravvy.gymtrack.controller;

import com.ravvy.gymtrack.dto.AlunoRequestDTO;
import com.ravvy.gymtrack.dto.AlunoResponseDTO;
import com.ravvy.gymtrack.dto.mapper.MapperAluno;
import com.ravvy.gymtrack.model.Aluno;
import com.ravvy.gymtrack.service.AlunoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

    private AlunoService alunoService;
    private final MapperAluno mapperAluno;

    public AlunoController(AlunoService alunoService, MapperAluno mapperAluno) {
        this.alunoService = alunoService;
        this.mapperAluno = mapperAluno;
    }

    @PostMapping
    public void saveAluno(@RequestBody AlunoRequestDTO requestDTO) {
        alunoService.save(mapperAluno.requestToEntity(requestDTO));
    }

    @GetMapping("/{id}")
    public AlunoResponseDTO getAluno(@PathVariable Long id) {

        Aluno aluno = alunoService.buscarPorId(id);
        return mapperAluno.entityToResponseDTO(aluno);

    }

    @DeleteMapping("/{id}")
    public void deleteAluno(@PathVariable Long id) {
        Aluno aluno = alunoService.buscarPorId(id);
        alunoService.delete(aluno);
    }

}