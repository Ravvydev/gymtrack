package com.ravvy.gymtrack.controller;

import com.ravvy.gymtrack.dto.AvaliacaoCreatedRequest;
import com.ravvy.gymtrack.dto.AvaliacaoResponse;
import com.ravvy.gymtrack.dto.AvaliacaoUploadRequest;
import com.ravvy.gymtrack.dto.AvaliacoesDoDiaResponse;
import com.ravvy.gymtrack.service.AvaliacaoService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/avaliacao")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    public AvaliacaoController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @PostMapping
    public ResponseEntity<AvaliacaoResponse> criar(@RequestBody @Valid AvaliacaoCreatedRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(avaliacaoService.criar(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoResponse> getAvaliacao(@PathVariable Long id) {
        return ResponseEntity.ok(
                avaliacaoService.buscarResponsePorId(id)
        );
    }

    @GetMapping("/{idProfessor}/data/{dataCriacao}")
    public ResponseEntity<AvaliacoesDoDiaResponse> buscarAvaliacoesPorData(@PathVariable Long idProfessor,

                                                                           @DateTimeFormat(pattern = "dd-MM-yyyy")
                                                                           @PathVariable LocalDate dataCriacao) {

        List<AvaliacaoResponse> avaliacoesDoDia =
                avaliacaoService.buscarAvaliacoesPorData(
                        idProfessor, dataCriacao
                );

        return ResponseEntity.ok(
                new AvaliacoesDoDiaResponse(
                        avaliacoesDoDia.size(),
                        avaliacoesDoDia
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvaliacao(@PathVariable Long id) {
        avaliacaoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AvaliacaoResponse> upload(
            @RequestBody @Valid AvaliacaoUploadRequest request,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                avaliacaoService.upload(request, id)
        );
    }

}
