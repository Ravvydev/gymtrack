package com.ravvy.gymtrack.controller;

import com.ravvy.gymtrack.dto.InstituicaoCreateRequest;
import com.ravvy.gymtrack.dto.InstituicaoResponse;
import com.ravvy.gymtrack.dto.InstituicaoUpdateRequest;
import com.ravvy.gymtrack.dto.UpdateSenha;
import com.ravvy.gymtrack.model.Instituicao;
import com.ravvy.gymtrack.service.InstituicaoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instituição")
public class InstituicaoController {

    private final InstituicaoService instituicaoService;

    public InstituicaoController(InstituicaoService instituicaoService) {
        this.instituicaoService = instituicaoService;
    }

    @PostMapping("/save")
    private void saveInstituicao(@RequestBody @Valid InstituicaoCreateRequest request){
        instituicaoService.save(request);
    }

    @GetMapping("/{id}")
    private InstituicaoResponse getInstituicao(@PathVariable Long id){
        Instituicao instituicao = instituicaoService.buscarPorId(id);
        return instituicaoService.toResponse(instituicao);
    }
    @GetMapping("/listar-instituições")
    public List<Instituicao> listarTodosInstituicao() {
        return instituicaoService.listarTodosInstituicao();
    }

    @DeleteMapping("/delete/{id}")
    public  void deleteInstituicao(@PathVariable Long id){
        instituicaoService.deleteById(id);
    }

    @PutMapping("/{id}")
    public InstituicaoResponse update(@RequestBody @Valid InstituicaoUpdateRequest request,
                                      @PathVariable Long id) {
        return instituicaoService.update(request, id);
    }

    @PutMapping("/{id}/senha")
    public void updateSenha(@RequestBody @Valid UpdateSenha request,
                            @PathVariable Long id) {
        instituicaoService.updateSenha(request, id);
    }

}
