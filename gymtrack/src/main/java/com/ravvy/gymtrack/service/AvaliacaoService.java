package com.ravvy.gymtrack.service;

import com.ravvy.gymtrack.model.Aluno;
import com.ravvy.gymtrack.model.Avaliacao;
import com.ravvy.gymtrack.repository.AvaliacaoRepository;
import com.ravvy.gymtrack.util.TipoClassificacao;
import com.ravvy.gymtrack.util.TipoSexoBiologico;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Getter
@Setter
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final ClassificacaoImcService  classificacaoImcService;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository, ClassificacaoImcService classificacaoImcService) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.classificacaoImcService = classificacaoImcService;
    }

    public void salvar(Avaliacao avaliacao) {

        if (avaliacao == null) {
            throw new IllegalStateException("Avaliação não pode ser nula!");
        }

        if (avaliacao.getAluno() == null) {
            throw new IllegalStateException("O aluno não pode ser nulo!");
        }

        Double imc = calcularIMC(avaliacao);
        avaliacao.setImc(imc);

        Double rce = calcularRCE(avaliacao);
        avaliacao.setRce(rce);

        avaliacao.setDataAvaliacao(LocalDateTime.now());

        TipoClassificacao classificacao =
                calcularTipoClassificacao(avaliacao.getAluno() , imc);

        avaliacao.setTipoClassificacao(classificacao);

        avaliacaoRepository.save(avaliacao);

    }

    public void deleteById(Long id) {
        Avaliacao avaliacao = buscarPorId(id);
        avaliacaoRepository.delete(avaliacao);
    }

    public Avaliacao buscarPorId(Long id) {
        return avaliacaoRepository.findById(id)
                .orElseThrow(()
                        -> new EntityNotFoundException("Avaliação não encontrada no id " + id));
    }

    private Double calcularIMC(Avaliacao avaliacao) {
        return avaliacao.getPeso() /
                (avaliacao.getAltura() * avaliacao.getAltura());
    }

    private Double calcularRCE(Avaliacao avaliacao) {
        return avaliacao.getPerimetroCintura() /
                avaliacao.getAltura();
    }

    private TipoClassificacao calcularTipoClassificacao(Aluno aluno, Double imc) {
        return classificacaoImcService.classificar(
                                aluno.getSexo(),
                                YearOldService.calcularIdade(aluno.getDataNascimento()),
                                imc
                        );
    }

}
