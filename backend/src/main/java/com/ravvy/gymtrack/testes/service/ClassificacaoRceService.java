package com.ravvy.gymtrack.testes.service;

import com.ravvy.gymtrack.avaliacao.enums.TipoClassificacao;
import org.springframework.stereotype.Service;

@Service
public class ClassificacaoRceService {

    public TipoClassificacao classificar(double rce) {

        if (rce <= 0.50) {
            return TipoClassificacao.ZONA_SAUDAVEL;
        }

        return TipoClassificacao.ZONA_DE_RISCO;
    }
}