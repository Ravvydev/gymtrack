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

    // Serviços e repositórios utilizados para buscar os dados e classificar os resultados.
    private final InstituicaoService instituicaoService;
    private final AvaliacaoRepository avaliacaoRepository;
    private final ClassificacaoRceService classificacaoRceService;
    private final ClassificacaoImcService classificacaoImcService;

    // Injeta as dependências necessárias para gerar o relatório institucional.
    public RelatorioEvolucaoInstitucionalService(InstituicaoService instituicaoService, InstituicaoRepository instituicaoRepository, AvaliacaoRepository avaliacaoRepository, ClassificacaoRceService classificacaoRceService, ClassificacaoImcService classificacaoImcService) {
        this.instituicaoService = instituicaoService;
        this.avaliacaoRepository = avaliacaoRepository;
        this.classificacaoRceService = classificacaoRceService;
        this.classificacaoImcService = classificacaoImcService;
    }

    /// Método que gera o relatório de todos os estudantes de uma única instituição de uma vez.
    // Indica que o método realiza apenas consultas, sem modificar os dados do banco.
    @Transactional(readOnly = true)
    public RelatorioEvolucaoInstitucionalResponse getRelatorioInstituicao(
            Long idInstituicao
    ) {

        // Verifica se o ID da instituição é nulo.
        if (idInstituicao == null) {
            throw new RegraDeNegocioExeption(
                    "O id da instituição não pode ser nulo"
            );
        }

        // Encontra a instituição pelo ID informado.
        Instituicao instituicao = instituicaoService.buscarPorId(idInstituicao);

        // Pega todas as avaliações de todos os alunos da instituição.
        // Em ordem da mais antiga para a mais recente.
        List<Avaliacao> avaliacoes = avaliacaoRepository
                .findByAlunoInstituicaoIdOrderByDataAvaliacaoAsc(idInstituicao);

        // Armazena os IDs dos alunos em um conjunto para evitar contagens duplicadas.
        Set<Long> idsAlunos = new HashSet<>();

        // Percorre todas as avaliações para identificar os alunos que possuem registros.
        for (Avaliacao avaliacao : avaliacoes) {
            Long idAluno = avaliacao.getAluno().getId();
            idsAlunos.add(idAluno);
        }

        // Obtém a quantidade total de alunos distintos encontrados nas avaliações.
        int quantidadeTotalAlunos = idsAlunos.size();

        // Verifica se a instituição tem avaliações e, consequentemente, alunos avaliados.
        if (avaliacoes.isEmpty()) {
            throw new RegraDeNegocioExeption(
                    "A instituição não tem avaliações cadastradas"
            );
        }

        /// Entramos nas avaliações daquele mês.

        // Map para separar todas as avaliações por mês.
        // A chave representa o mês e o valor contém as avaliações realizadas nele.
        Map<YearMonth, List<Avaliacao>> avaliacoesPorMes = new LinkedHashMap<>();

        // Percorre as avaliações para agrupá-las de acordo com o mês em que foram realizadas.
        for (Avaliacao avaliacao : avaliacoes) {

            // Pega a data de cada avaliação e extrai o mês e o ano.
            YearMonth mes = YearMonth.from(avaliacao.getDataAvaliacao());

            // Verifica se o mês encontrado já foi adicionado ao map.
            if (!avaliacoesPorMes.containsKey(mes)) {

                // Caso o mês ainda não esteja no map, cria uma lista vazia para ele.
                avaliacoesPorMes.put(mes, new ArrayList<>());
            }

            // Adiciona a avaliação à lista correspondente ao mês encontrado.
            avaliacoesPorMes.get(mes).add(avaliacao);
        }

        // Cria uma lista para armazenar os relatórios mensais que serão gerados.
        List<RelatorioMensalInstitucionalResponse> relatoriosMensais = new ArrayList<>();

        // Percorre as entradas do map para gerar um relatório para cada mês.
        for (Map.Entry<YearMonth, List<Avaliacao>> entrada : avaliacoesPorMes.entrySet()) {

            // Obtém o mês armazenado na chave da entrada atual.
            YearMonth mes = entrada.getKey();

            // Obtém a lista de avaliações correspondentes ao mês atual.
            List<Avaliacao> avaliacoesDoMes = entrada.getValue();

            // Cria um map para guardar apenas a avaliação mais recente de cada aluno no mês.
            // A chave é o ID do aluno e o valor é a avaliação correspondente.
            Map<Long, Avaliacao> avaliacaoMaisRecentePorAluno = new HashMap<>();

            // Percorre as avaliações realizadas durante o mês atual.
            for (Avaliacao avaliacao : avaliacoesDoMes) {

                // Obtém o ID do aluno a partir da avaliação que está sendo percorrida.
                Long idAluno = avaliacao.getAluno().getId();

                // Adiciona a chave (ID) e o valor (avaliação) ao map.
                // Isso funciona porque um map não mantém duas entradas com a mesma chave.
                // Se uma chave já existe, o método put substitui o valor associado a ela.
                // Como as avaliações foram consultadas em ordem cronológica crescente,
                // a última avaliação inserida para cada aluno é a mais recente daquele mês.
                avaliacaoMaisRecentePorAluno.put(idAluno, avaliacao);
            }

            // Obtém, pelo size, a quantidade de alunos avaliados naquele mês,
            // considerando cada aluno apenas uma vez.
            int quantidadeAlunosMes = avaliacaoMaisRecentePorAluno.size();

            // Inicializa as variáveis que acumularão os valores de IMC e RCE do mês.
            double somaImcMes = 0.0;
            double somaRceMes = 0.0;

            // Percorre as avaliações mais recentes de cada aluno e soma seus valores de IMC e RCE.
            for (Avaliacao avaliacao : avaliacaoMaisRecentePorAluno.values()) {
                somaImcMes += avaliacao.getImc();
                somaRceMes += avaliacao.getRce();
            }

            // Inicializa as médias e a classificação como null.
            // Esses valores só serão definidos quando houver alunos avaliados no mês.
            Double imcMedio = null;
            Double rceMedio = null;
            TipoClassificacao classificacaoRce = null;

            // Calcula as médias mensais e classifica a média de RCE quando há alunos avaliados.
            if (quantidadeAlunosMes > 0) {
                imcMedio = somaImcMes / quantidadeAlunosMes;
                rceMedio = somaRceMes / quantidadeAlunosMes;

                // Classifica o valor médio de RCE usando o serviço responsável por essa regra.
                classificacaoRce =
                        classificacaoRceService.classificar(rceMedio);
            }

            // Cria a lista que reunirá os resultados de todas as faixas etárias do mês.
            List<FaixaEtaria> responseAllFaixaEtaria = new ArrayList<>();

            // Calcula os indicadores dos alunos com idades entre 6 e 8 anos.
            FaixaEtaria faixaEtaria6a8 = calcularFaixaEtaria(
                    6,
                    8,
                    quantidadeAlunosMes,
                    avaliacaoMaisRecentePorAluno
            );

            // Calcula os indicadores dos alunos com idades entre 9 e 11 anos.
            FaixaEtaria faixaEtaria9a11 = calcularFaixaEtaria(
                    9,
                    11,
                    quantidadeAlunosMes,
                    avaliacaoMaisRecentePorAluno
            );

            // Calcula os indicadores dos alunos com idades entre 12 e 14 anos.
            FaixaEtaria faixaEtaria12a14 = calcularFaixaEtaria(
                    12,
                    14,
                    quantidadeAlunosMes,
                    avaliacaoMaisRecentePorAluno
            );

            // Calcula os indicadores dos alunos com idades entre 15 e 17 anos.
            FaixaEtaria faixaEtaria15a17 = calcularFaixaEtaria(
                    15,
                    17,
                    quantidadeAlunosMes,
                    avaliacaoMaisRecentePorAluno
            );

            // Adiciona os resultados das quatro faixas etárias à lista do relatório mensal.
            responseAllFaixaEtaria.add(faixaEtaria6a8);
            responseAllFaixaEtaria.add(faixaEtaria9a11);
            responseAllFaixaEtaria.add(faixaEtaria12a14);
            responseAllFaixaEtaria.add(faixaEtaria15a17);

            // Monta o relatório referente ao mês com as médias, a classificação de RCE
            // e os resultados agrupados por faixa etária.
            RelatorioMensalInstitucionalResponse relatorioMensal =
                    new RelatorioMensalInstitucionalResponse(
                            mes,
                            quantidadeAlunosMes,
                            imcMedio,
                            rceMedio,
                            classificacaoRce,
                            responseAllFaixaEtaria
                    );

            // Adiciona o relatório mensal à lista que será retornada ao final do método.
            relatoriosMensais.add(relatorioMensal);
        }

        // Monta a resposta final com os dados da instituição,
        // a quantidade total de alunos distintos e os relatórios mensais.
        return new RelatorioEvolucaoInstitucionalResponse(
                idInstituicao,
                instituicao.getNome(),
                quantidadeTotalAlunos,
                relatoriosMensais
        );
    }

    // Calcula os indicadores de uma faixa etária específica para determinado mês.
    private FaixaEtaria calcularFaixaEtaria(
            int idadeMinima,
            int idadeMaxima,
            int quantidadeAlunosMes,
            Map<Long, Avaliacao> avaliacoesPorAluno
    ) {

        // Inicializa os contadores e acumuladores utilizados nos cálculos da faixa.
        int quantidadeAlunosNaFaixa = 0;
        double somaImcNaFaixa = 0;
        int quantidadeAlunosSaudaveisImcNaFaixa = 0;
        int quantidadeAlunosRiscoImcNaFaixa = 0;
        double somaRceNaFaixa = 0;
        int quantidadeAlunosSaudaveisRceNaFaixa = 0;
        int quantidadeAlunosRiscoRceNaFaixa = 0;

        // Inicializa o percentual e as médias.
        // As médias começam como null porque ainda não sabemos se há alunos na faixa.
        double percentualAlunos = 0.0;
        Double imcMedio = null;
        Double rceMedio = null;

        // Inicializa a classificação de IMC como null.
        // Ela será definida pela classificação predominante entre os alunos da faixa.
        TipoClassificacao classificacaoImc = null;

        // Percorre as avaliações mais recentes dos alunos no mês.
        for (Avaliacao avaliacao : avaliacoesPorAluno.values()) {

            // Obtém a idade registrada na avaliação atual.
            int idade = avaliacao.getIdade();

            // Verifica se a idade do aluno está dentro dos limites da faixa considerada.
            if (idade >= idadeMinima && idade <= idadeMaxima) {

                // Contabiliza o aluno e acumula seus valores de IMC e RCE.
                quantidadeAlunosNaFaixa++;
                somaImcNaFaixa += avaliacao.getImc();
                somaRceNaFaixa += avaliacao.getRce();

                // Classifica o IMC do aluno considerando sexo biológico, idade e valor de IMC.
                TipoClassificacao tipoClassificacaoImc = classificacaoImcService.classificar(
                        avaliacao.getSexo(),
                        idade,
                        avaliacao.getImc()
                );

                // Incrementa o contador correspondente à classificação de IMC obtida.
                if (tipoClassificacaoImc == TipoClassificacao.ZONA_SAUDAVEL) {
                    quantidadeAlunosSaudaveisImcNaFaixa++;
                } else if (tipoClassificacaoImc == TipoClassificacao.ZONA_DE_RISCO) {
                    quantidadeAlunosRiscoImcNaFaixa++;
                }

                // Classifica o RCE do aluno para contabilizar os resultados da faixa.
                TipoClassificacao tipoClassificacaoRce = classificacaoRceService
                        .classificar(
                                avaliacao.getRce()
                        );

                // Incrementa o contador correspondente à classificação de RCE obtida.
                if (tipoClassificacaoRce == TipoClassificacao.ZONA_SAUDAVEL) {
                    quantidadeAlunosSaudaveisRceNaFaixa++;
                } else if (tipoClassificacaoRce == TipoClassificacao.ZONA_DE_RISCO) {
                    quantidadeAlunosRiscoRceNaFaixa++;
                }
            }
        }

        // Calcula as médias de IMC e RCE e o percentual de alunos da faixa
        // somente quando pelo menos um aluno pertence a ela.
        if (quantidadeAlunosNaFaixa != 0) {
            imcMedio = somaImcNaFaixa / quantidadeAlunosNaFaixa;
            rceMedio = somaRceNaFaixa / quantidadeAlunosNaFaixa;

            // Calcula a participação percentual da faixa em relação ao total de alunos do mês.
            percentualAlunos = (double) quantidadeAlunosNaFaixa / quantidadeAlunosMes * 100;
        }

        // Define a classificação de IMC da faixa pela maioria das classificações individuais.
        // Se houver mais alunos saudáveis do que alunos em risco, a faixa será saudável.
        if (quantidadeAlunosSaudaveisImcNaFaixa > quantidadeAlunosRiscoImcNaFaixa) {
            classificacaoImc = TipoClassificacao.ZONA_SAUDAVEL;

            // Se houver mais alunos em risco do que alunos saudáveis, a faixa será de risco.
        } else if (quantidadeAlunosSaudaveisImcNaFaixa < quantidadeAlunosRiscoImcNaFaixa) {
            classificacaoImc = TipoClassificacao.ZONA_DE_RISCO;
        }

        // Inicializa a classificação de RCE como null.
        TipoClassificacao classificacaoRce = null;

        // Só classifica a média de RCE quando ela foi calculada,
        // ou seja, quando há pelo menos um aluno na faixa etária.
        if (rceMedio != null) {
            classificacaoRce = classificacaoRceService.classificar(
                    rceMedio
            );
        }

        // Cria o objeto que representa os resultados calculados para a faixa etária.
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