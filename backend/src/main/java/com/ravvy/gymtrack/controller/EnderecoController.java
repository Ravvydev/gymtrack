package com.ravvy.gymtrack.controller;

import com.ravvy.gymtrack.dto.EnderecoCreateRequest;
import com.ravvy.gymtrack.dto.EnderecoResponse;
import com.ravvy.gymtrack.service.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<EnderecoResponse> criar(
            @RequestBody @Valid EnderecoCreateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        enderecoService.save(request)
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoResponse> buscarPorId(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                enderecoService.buscarResponsePorId(id)
        );
    }
}