package com.ravvy.gymtrack.relatorio.controller;

import com.ravvy.gymtrack.relatorio.dto.RelatorioEvolucaoResponse;
import com.ravvy.gymtrack.relatorio.service.RelatorioEvolucaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/relatorio")
public class RelatorioController {

    private final RelatorioEvolucaoService relatorioEvolucaoService;

    public RelatorioController(RelatorioEvolucaoService relatorioEvolucaoService) {
        this.relatorioEvolucaoService = relatorioEvolucaoService;
    }

    @GetMapping("/{alunoId}")
    public ResponseEntity<RelatorioEvolucaoResponse> gerarRelatorioInstitucional(
            @PathVariable Long alunoId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                relatorioEvolucaoService.buscarRelatorio(alunoId)
        );

    }

}