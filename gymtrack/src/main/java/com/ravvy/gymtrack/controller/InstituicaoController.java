package com.ravvy.gymtrack.controller;

import com.ravvy.gymtrack.dto.InstituicaoRequestDTO;
import com.ravvy.gymtrack.dto.InstituicaoResponseDTO;
import com.ravvy.gymtrack.dto.mapper.InstituicaoMapper;
import com.ravvy.gymtrack.model.Instituicao;
import com.ravvy.gymtrack.service.InstituicaoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instituição")
public class InstituicaoController {

    private InstituicaoService instituicaoService;
    private InstituicaoMapper mapper;

    @PostMapping("/save")
    private void saveInstituicao(@RequestBody @Valid InstituicaoRequestDTO request){
        instituicaoService.save(mapper.requestToEntity(request));
    }

    @GetMapping("/{id}")
    private InstituicaoResponseDTO getInstituicao(@PathVariable @Valid Long id){
        Instituicao instituicao = instituicaoService.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Instituição não encontrada no id " + id));

        return mapper.entityToResponseDTO(instituicao);
    }
    @GetMapping("/listar-instituições")
    public List<Instituicao> listarTodosInstituicao() {
        return instituicaoService.listarTodosInstituicao();
    }

    @DeleteMapping("/delete/{id}")
    public  void deleteInstituicao(@PathVariable @Valid Long id){
        Instituicao instituicao = instituicaoService.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Instituição não encontrada no id " + id));

        instituicaoService.delete(instituicao);
    }

}
