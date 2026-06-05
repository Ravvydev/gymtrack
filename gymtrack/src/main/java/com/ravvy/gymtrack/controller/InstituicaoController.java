package com.ravvy.gymtrack.controller;

import com.ravvy.gymtrack.dto.InstituicaoCreateRequest;
import com.ravvy.gymtrack.dto.InstituicaoResponse;
import com.ravvy.gymtrack.dto.mapper.InstituicaoMapper;
import com.ravvy.gymtrack.model.Instituicao;
import com.ravvy.gymtrack.service.InstituicaoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instituição")
public class InstituicaoController {

    private final InstituicaoService instituicaoService;
    private final InstituicaoMapper instituicaoMapper;

    public InstituicaoController(InstituicaoService instituicaoService, InstituicaoMapper instituicaoMapper) {
        this.instituicaoService = instituicaoService;
        this.instituicaoMapper = instituicaoMapper;
    }

    @PostMapping("/save")
    private void saveInstituicao(@RequestBody @Valid InstituicaoCreateRequest request){
        instituicaoService.save(instituicaoMapper.toEntity(request));
    }

    @GetMapping("/{id}")
    private InstituicaoResponse getInstituicao(@PathVariable @Valid Long id){
        Instituicao instituicao = instituicaoService.buscarPorId(id);
        return instituicaoMapper.toResponse(instituicao);
    }
    @GetMapping("/listar-instituições")
    public List<Instituicao> listarTodosInstituicao() {
        return instituicaoService.listarTodosInstituicao();
    }

    @DeleteMapping("/delete/{id}")
    public  void deleteInstituicao(@PathVariable @Valid Long id){
        instituicaoService.deleteById(id);
    }

}
