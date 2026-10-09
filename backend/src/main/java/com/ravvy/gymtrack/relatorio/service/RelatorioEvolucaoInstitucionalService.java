package com.ravvy.gymtrack.relatorio.service;

import com.ravvy.gymtrack.avaliacao.entity.Avaliacao;
import com.ravvy.gymtrack.avaliacao.enums.TipoClassificacao;
import com.ravvy.gymtrack.avaliacao.repository.AvaliacaoRepository;
import com.ravvy.gymtrack.instituicao.entity.Instituicao;
import com.ravvy.gymtrack.instituicao.repository.InstituicaoRepository;
import com.ravvy.gymtrack.instituicao.service.InstituicaoService;
import com.ravvy.gymtrack.relatorio.dto.RelatorioEvolucaoInstitucionalResponse;
import com.ravvy.gymtrack.relatorio.dto.RelatorioMensalInstitucionalResponse;
import com.ravvy.gymtrack.relatorio.faixasEtaria.FaixaEtaria;
import com.ravvy.gymtrack.shared.exception.RegraDeNegocioExeption;
import com.ravvy.gymtrack.testes.classificacao.ClassificacaoImcService;
import com.ravvy.gymtrack.testes.classificacao.ClassificacaoRceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.*;

@Service
public class RelatorioEvolucaoInstitucionalService {

    private final InstituicaoService instituicaoService;
    private final InstituicaoRepository instituicaoRepository;
    private final AvaliacaoRepository avaliacaoRepository;
    private final ClassificacaoRceService classificacaoRceService;
    private final ClassificacaoImcService classificacaoImcService;

    public RelatorioEvolucaoInstitucionalService(InstituicaoService instituicaoService, InstituicaoRepository instituicaoRepository, AvaliacaoRepository avaliacaoRepository, ClassificacaoRceService classificacaoRceService, ClassificacaoImcService classificacaoImcService) {
        this.instituicaoService = instituicaoService;
        this.instituicaoRepository = instituicaoRepository;
        this.avaliacaoRepository = avaliacaoRepository;
        this.classificacaoRceService = classificacaoRceService;
        this.classificacaoImcService = classificacaoImcService;
    }

    /// Método que gera o relatório de todos os estudantes de uma unica instituiçaõ de uma vez
    @Transactional(readOnly = true)
    public RelatorioEvolucaoInstitucionalResponse getRelatorioInstituicao(
            Long idInstituicao
    ) {

        // Verifica se o id da instituição é nulo
        if (idInstituicao == null) {
            throw new RegraDeNegocioExeption(
                    "O id da instituição não pode ser nulo"
            );
        }

        // Encontra a instituição
        Instituicao instituicao = instituicaoService.buscarPorId(idInstituicao);

        // Pega todas as avaliações de todos os alunos da instituição
        // Em ordem da mais antiga para a mais recente
        List<Avaliacao> avaliacoes = avaliacaoRepository
                .findByAlunoInstituicaoIdOrderByDataAvaliacaoAsc(idInstituicao);

        Set<Long> idsAlunos = new HashSet<>();
        for (Avaliacao avaliacao : avaliacoes) {
            Long idAluno = avaliacao.getAluno().getId();
            idsAlunos.add(idAluno);
        }

        int quantidadeTotalAlunos = idsAlunos.size();

        // Verifica se a instituição tem avaliações e consequentemente alunos
        if (avaliacoes.isEmpty()) {
            throw new RegraDeNegocioExeption(
                    "A instituição não tem avaliações cadastradas"
            );
        }

        /// Entramos nas avaliações daquele mês

        // Map para separar todas as avaliações por mês.
        Map<YearMonth, List<Avaliacao>> avaliacoesPorMes = new HashMap<>();

        for (Avaliacao avaliacao : avaliacoes) {

            // Pega a data de cada avaliação e extrai o mês
            YearMonth mes = YearMonth.from(avaliacao.getDataAvaliacao());

            // Verifica se o mês encontrado já foi adicionado ao map
            if (!avaliacoesPorMes.containsKey(mes)) {

                // Caso, o mês ainda não esteja no map, cria e adiciona uma
                avaliacoesPorMes.put(mes, new ArrayList<>());
            }

            // Caso não, adiciona o mês e a avaliação respectiva
            avaliacoesPorMes.get(mes).add(avaliacao);
        }

        // Cria uma lista dos relatorios mensais
        List<RelatorioMensalInstitucionalResponse> relatoriosMensais = new ArrayList<>();

        // Entra no map AvaliacoesPorMes
        for (Map.Entry<YearMonth, List<Avaliacao>> entrada : avaliacoesPorMes.entrySet()) {

            // Cria uma variavel mes e coloca nela o valor do mês que estava no map
            YearMonth mes = entrada.getKey();

            // Cria uma lista das avaliações respcetivas daquele mês
            // Da a ela todas as avaliações da respectiva key(mes)
            List<Avaliacao> avaliacoesDoMes = entrada.getValue();

            // Cria uma lista onde serão guardadas apenas as avaliações mais recentes
            // de cada aluno de acordo com o id do Aluno
            Map<Long, Avaliacao> avaliacaoMaisRecentePorAluno = new HashMap<>();

            // For para percorrer as avaliações que foram feitas em cada mês
            for (Avaliacao avaliacao : avaliacoesDoMes) {

                // Pega o id do aluno apartir da avaliação que está sendo percorrida
                Long idAluno = avaliacao.getAluno().getId();

                // Adiciona a chave(id) e o valor(avaliacao) a cada loop

                // Isso funciona porque. Map não aceita chaves duplicadas.
                // Se você tentar inserir uma chave que já existe, o metodo put
                // apenas sobreescreve o valor antigo associado a ela.
                avaliacaoMaisRecentePorAluno.put(idAluno, avaliacao);
            }

            // pega a quantidade de alunos avaliados naquele mês atravez no size
            int quantidadeAlunosMes = avaliacaoMaisRecentePorAluno.size();

            double somaImcMes = 0.0;
            double somaRceMes = 0.0;

            for (Avaliacao avaliacao : avaliacaoMaisRecentePorAluno.values()) {
                somaImcMes += avaliacao.getImc();
                somaRceMes += avaliacao.getRce();
            }

            Double imcMedio = null;
            Double rceMedio = null;
            TipoClassificacao classificacaoRce = null;

            if (quantidadeAlunosMes > 0) {
                imcMedio = somaImcMes / quantidadeAlunosMes;
                rceMedio = somaRceMes / quantidadeAlunosMes;

                classificacaoRce =
                        classificacaoRceService.classificar(rceMedio);
            }

            List<FaixaEtaria> responseAllFaixaEtaria = new ArrayList<>();

            FaixaEtaria faixaEtaria6a8 = calcularFaixaEtaria(
                    6,
                    8,
                    quantidadeAlunosMes,
                    avaliacaoMaisRecentePorAluno
            );

            FaixaEtaria faixaEtaria9a11 = calcularFaixaEtaria(
                    9,
                    11,
                    quantidadeAlunosMes,
                    avaliacaoMaisRecentePorAluno
            );

            FaixaEtaria faixaEtaria12a14 = calcularFaixaEtaria(
                    12,
                    14,
                    quantidadeAlunosMes,
                    avaliacaoMaisRecentePorAluno
            );

            FaixaEtaria faixaEtaria15a17 = calcularFaixaEtaria(
                    15,
                    17,
                    quantidadeAlunosMes,
                    avaliacaoMaisRecentePorAluno
            );

            responseAllFaixaEtaria.add(faixaEtaria6a8);
            responseAllFaixaEtaria.add(faixaEtaria9a11);
            responseAllFaixaEtaria.add(faixaEtaria12a14);
            responseAllFaixaEtaria.add(faixaEtaria15a17);

            // Monta o relatório referente ao mês.
            RelatorioMensalInstitucionalResponse relatorioMensal =
                    new RelatorioMensalInstitucionalResponse(
                            mes,
                            quantidadeAlunosMes,
                            imcMedio,
                            rceMedio,
                            classificacaoRce,
                            responseAllFaixaEtaria
                    );

            relatoriosMensais.add(relatorioMensal);
        }

        return new RelatorioEvolucaoInstitucionalResponse(
                idInstituicao,
                instituicao.getNome(),
                quantidadeTotalAlunos,
                relatoriosMensais
        );
    }

    private FaixaEtaria calcularFaixaEtaria(
            int idadeMinima,
            int idadeMaxima,
            int quantidadeAlunosMes,
            Map<Long, Avaliacao> avaliacoesPorAluno
    ) {

        int quantidadeAlunosNaFaixa = 0;
        double somaImcNaFaixa = 0;
        int quantidadeAlunosSaudaveisImcNaFaixa = 0;
        int quantidadeAlunosRiscoImcNaFaixa = 0;
        double somaRceNaFaixa = 0;
        int quantidadeAlunosSaudaveisRceNaFaixa = 0;
        int quantidadeAlunosRiscoRceNaFaixa = 0;
        double percentualAlunos = 0.0;
        Double imcMedio = null;
        Double rceMedio = null;
        TipoClassificacao classificacaoImc = null;
        for (Avaliacao avaliacao : avaliacoesPorAluno.values()) {

            int idade = avaliacao.getIdade();

            if (idade >= idadeMinima && idade <= idadeMaxima) {
                quantidadeAlunosNaFaixa++;
                somaImcNaFaixa += avaliacao.getImc();
                somaRceNaFaixa += avaliacao.getRce();

                TipoClassificacao tipoClassificacaoImc = classificacaoImcService.classificar(
                        avaliacao.getSexo(),
                        idade,
                        avaliacao.getImc()
                );

                if (tipoClassificacaoImc == TipoClassificacao.ZONA_SAUDAVEL) {
                    quantidadeAlunosSaudaveisImcNaFaixa++;
                } else if (tipoClassificacaoImc == TipoClassificacao.ZONA_DE_RISCO) {
                    quantidadeAlunosRiscoImcNaFaixa++;
                }

                TipoClassificacao tipoClassificacaoRce = classificacaoRceService
                        .classificar(
                                avaliacao.getRce()
                        );

                if (tipoClassificacaoRce == TipoClassificacao.ZONA_SAUDAVEL) {
                    quantidadeAlunosSaudaveisRceNaFaixa++;
                } else if (tipoClassificacaoRce == TipoClassificacao.ZONA_DE_RISCO) {
                    quantidadeAlunosRiscoRceNaFaixa++;
                }
            }

        }

        if (quantidadeAlunosNaFaixa != 0) {
            imcMedio = somaImcNaFaixa / quantidadeAlunosNaFaixa;
            rceMedio = somaRceNaFaixa / quantidadeAlunosNaFaixa;
            percentualAlunos = (double) quantidadeAlunosNaFaixa / quantidadeAlunosMes * 100;
        }

        if (quantidadeAlunosSaudaveisImcNaFaixa > quantidadeAlunosRiscoImcNaFaixa) {
            classificacaoImc = TipoClassificacao.ZONA_SAUDAVEL;
        } else if (quantidadeAlunosSaudaveisImcNaFaixa < quantidadeAlunosRiscoImcNaFaixa) {
            classificacaoImc = TipoClassificacao.ZONA_DE_RISCO;
        }

        TipoClassificacao classificacaoRce = null;
        if (rceMedio != null) {
            classificacaoRce = classificacaoRceService.classificar(
                    rceMedio
            );
        }

        return new FaixaEtaria(
                idadeMinima,
                idadeMaxima,
                percentualAlunos,
                quantidadeAlunosNaFaixa,
                imcMedio,
                classificacaoImc,
                quantidadeAlunosSaudaveisImcNaFaixa,
                quantidadeAlunosRiscoImcNaFaixa,
                rceMedio,
                classificacaoRce,
                quantidadeAlunosSaudaveisRceNaFaixa,
                quantidadeAlunosRiscoRceNaFaixa
        );
    }

}