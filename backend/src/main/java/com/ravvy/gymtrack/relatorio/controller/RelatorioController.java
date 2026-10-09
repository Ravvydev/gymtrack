package com.ravvy.gymtrack.relatorio.controller;

import com.ravvy.gymtrack.relatorio.dto.RelatorioEvolucaoResponse;
import com.ravvy.gymtrack.relatorio.service.RelatorioEvolucaoService;
import org.hibernate.annotations.processing.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/relatorio")
public class RelatorioController {

    private final RelatorioEvolucaoService relatorioEvolucaoService;

    public RelatorioController(RelatorioEvolucaoService relatorioEvolucaoService) {
        this.relatorioEvolucaoService = relatorioEvolucaoService;
    }

    @GetMapping("/{alunoId}")
    public ResponseEntity<RelatorioEvolucaoResponse> gerarRelatorio(
            @PathVariable Long alunoId
    ){
        return ResponseEntity.status(HttpStatus.OK).body(
                relatorioEvolucaoService.buscarRelatorio(alunoId)
        );

    }

}