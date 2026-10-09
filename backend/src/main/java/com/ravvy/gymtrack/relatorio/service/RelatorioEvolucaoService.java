package com.ravvy.gymtrack.relatorio.service;

import com.ravvy.gymtrack.aluno.entity.Aluno;
import com.ravvy.gymtrack.aluno.service.AlunoService;
import com.ravvy.gymtrack.avaliacao.entity.Avaliacao;
import com.ravvy.gymtrack.avaliacao.repository.AvaliacaoRepository;
import com.ravvy.gymtrack.relatorio.dto.EvolucaoAvaliacaoResponse;
import com.ravvy.gymtrack.relatorio.dto.RelatorioEvolucaoResponse;
import com.ravvy.gymtrack.shared.exception.RegraDeNegocioExeption;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class RelatorioEvolucaoService {

    private final AlunoService alunoService;
    private final AvaliacaoRepository avaliacaoRepository;

    public RelatorioEvolucaoService(AlunoService alunoService, AvaliacaoRepository avaliacaoRepository) {
        this.alunoService = alunoService;
        this.avaliacaoRepository = avaliacaoRepository;
    }

    @Transactional(readOnly = true)
    public RelatorioEvolucaoResponse buscarRelatorio(Long alunoId) {

        if (alunoId == null) {
            throw new RegraDeNegocioExeption("O id não pode ser nulo");
        }

        Aluno aluno = alunoService.buscarPorId(alunoId);

        if (aluno.getInstituicao() == null || aluno.getProfessor() == null) {
            throw new RegraDeNegocioExeption
                    ("O aluno precisa estar vinculado a um Professor e a uma instituição");
        }

        List<Avaliacao> avaliacoesAluno = avaliacaoRepository
                .findByAlunoIdOrderByDataAvaliacaoAsc(alunoId);

        if (avaliacoesAluno.isEmpty()) {
            throw new RegraDeNegocioExeption("O aluno precisa ter avaliações feitas");
        }

        LocalDate dataPrimeiraAvaliacao = avaliacoesAluno
                .getFirst().getDataAvaliacao();
        LocalDate dataUltimaAvaliacao = avaliacoesAluno
                .getLast().getDataAvaliacao();

        if (ChronoUnit.MONTHS.between(dataPrimeiraAvaliacao, dataUltimaAvaliacao) < 3) {
            throw new RegraDeNegocioExeption("O aluno precisa ter 3 ou mais meses de avaliação");
        }

        List<EvolucaoAvaliacaoResponse> evolucao = new ArrayList<>();

        for (Avaliacao avaliacao : avaliacoesAluno) {
            EvolucaoAvaliacaoResponse response = new EvolucaoAvaliacaoResponse(
                    avaliacao.getDataAvaliacao(),
                    avaliacao.getPeso(),
                    avaliacao.getAltura(),
                    avaliacao.getImc(),
                    avaliacao.getRce()
            );
            evolucao.add(response);
        }

        return new RelatorioEvolucaoResponse(
                aluno.getId(),
                aluno.getNome(),
                dataPrimeiraAvaliacao,
                dataUltimaAvaliacao,
                evolucao
        );
    }

}