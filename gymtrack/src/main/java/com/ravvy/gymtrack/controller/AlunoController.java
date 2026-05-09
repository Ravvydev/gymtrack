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
    private MapperAluno mapperAluno;

    @PostMapping("/POST/save-aluno")
    public void saveAluno(@RequestBody AlunoRequestDTO requestDTO) {
        alunoService.save(mapperAluno.requestToEntity(requestDTO));
    }

    @GetMapping("GET/get-aluno")
    public AlunoResponseDTO getAluno(@RequestParam Long id) {

        Aluno aluno = alunoService.findById(id)
                        .orElseThrow(() ->
                        new EntityNotFoundException("Aluno não encontrado por id " + id));

        return mapperAluno.entityToResponseDTO(aluno);
    }

    @DeleteMapping("/DELETE/delete-aluno")
    public void deleteAluno(@RequestParam Long id) {

        Aluno aluno = alunoService.findById(id)
                        .orElseThrow(() ->
                        new EntityNotFoundException("Aluno não encontrado por id " + id));

        alunoService.delete(aluno);
    }

}