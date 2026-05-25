package com.ravvy.gymtrack.controller;

import com.ravvy.gymtrack.model.Avaliacao;
import com.ravvy.gymtrack.service.AvaliacaoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/avaliacao")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    public AvaliacaoController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @PostMapping
    public void saveAvaliacao(@RequestBody @Valid Avaliacao avaliacao) {
        avaliacaoService.salvar(avaliacao);
    }

    @GetMapping("/{id}")
    public Avaliacao getAvaliacao(@PathVariable @Valid Long id) {
        return avaliacaoService.buscarPorId(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAvaliacao(@PathVariable @Valid Long id) {
        avaliacaoService.deleteById(id);
    }

}
