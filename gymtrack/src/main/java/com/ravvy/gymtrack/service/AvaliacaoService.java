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

    public void calcularIMC(Avaliacao avaliacao) {
        Double IMC = avaliacao.getPeso() /
                (avaliacao.getAltura() * avaliacao.getAltura());
        avaliacao.setImc(IMC);
    }

    public void calcularRCE(Avaliacao avaliacao) {
        Double perimetroCintura = avaliacao.getPerimetroCintura() / avaliacao.getAltura();
        avaliacao.setPerimetroCintura(perimetroCintura);
    }

}
