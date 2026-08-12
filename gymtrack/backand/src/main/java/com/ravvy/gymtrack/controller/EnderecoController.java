package com.ravvy.gymtrack.controller;

import com.ravvy.gymtrack.dto.EnderecoCreateRequest;
import com.ravvy.gymtrack.service.EnderecoService;
import com.ravvy.gymtrack.util.Endereco;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {

    private final EnderecoService enderecoService;

    public EnderecoController(
            EnderecoService enderecoService
    ) {
        this.enderecoService = enderecoService;
    }

    @PostMapping
    public Endereco criar(
            @RequestBody @Valid EnderecoCreateRequest request
    ) {
        return enderecoService.save(request);
    }

    @GetMapping("/{id}")
    public Endereco buscarPorId(
            @PathVariable Long id
    ) {
        return enderecoService.buscarPorId(id);
    }
}