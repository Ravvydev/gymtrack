package com.ravvy.gymtrack.service;

import com.ravvy.gymtrack.model.Avaliacao;
import com.ravvy.gymtrack.repository.AvaliacaoRepository;
import org.springframework.stereotype.Service;

@Service
public class AvaliacaoService {

    private AvaliacaoRepository avaliacaoRepository;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
    }

    public Double calcularIMC(Avaliacao avaliacao) {
        return avaliacao.getPeso() /
                (avaliacao.getAltura() * avaliacao.getAltura());
    }

    public Double calcularRCE(Avaliacao avaliacao) {
        return avaliacao.getPerimetroCintura() / avaliacao.getAltura();
    }

}
