package com.ravvy.gymtrack.controller;

import com.ravvy.gymtrack.dto.AvaliacaoCreatedRequest;
import com.ravvy.gymtrack.dto.AvaliacaoResponse;
import com.ravvy.gymtrack.dto.AvaliacaoUploadRequest;
import com.ravvy.gymtrack.dto.AvaliacoesDoDiaResponse;
import com.ravvy.gymtrack.dto.mapper.AvaliacaoMapper;
import com.ravvy.gymtrack.model.Avaliacao;
import com.ravvy.gymtrack.service.AvaliacaoService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/avaliacao")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;
    private final AvaliacaoMapper avaliacaoMapper;

    public AvaliacaoController(AvaliacaoService avaliacaoService, AvaliacaoMapper avaliacaoMapper) {
        this.avaliacaoService = avaliacaoService;
        this.avaliacaoMapper = avaliacaoMapper;
    }

    @PostMapping
    public AvaliacaoResponse criar(@RequestBody @Valid AvaliacaoCreatedRequest request) {
        return avaliacaoService.criar(request);
    }

    @GetMapping("/{id}")
    public AvaliacaoResponse getAvaliacao(@PathVariable Long id) {
        return avaliacaoService.buscarResponsePorId(id);
    }

    @GetMapping("/{idProfessor}/data/{dataCriacao}")
    public AvaliacoesDoDiaResponse buscarAvaliacoesPorData(@PathVariable Long idProfessor,

                                                           @DateTimeFormat(pattern = "dd-MM-yyyy")
                                                           @PathVariable LocalDate dataCriacao) {

        List<AvaliacaoResponse> avaliacoesDoDia =
                avaliacaoService.buscarAvaliacoesPorData(
                        idProfessor, dataCriacao
                );

        return new AvaliacoesDoDiaResponse(
                avaliacoesDoDia.size(),
                avaliacoesDoDia
        );
    }

    @DeleteMapping("/{id}")
    public void deleteAvaliacao(@PathVariable @Valid Long id) {
        avaliacaoService.deleteById(id);
    }

    @PutMapping("/{id}")
    public AvaliacaoResponse upload(
            @RequestBody @Valid AvaliacaoUploadRequest request,
            @PathVariable Long id
    ) {
        return avaliacaoService.upload(request, id);
    }

}
