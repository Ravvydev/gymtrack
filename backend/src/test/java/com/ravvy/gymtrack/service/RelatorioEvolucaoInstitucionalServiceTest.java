package com.ravvy.gymtrack.service;

import com.ravvy.gymtrack.aluno.entity.Aluno;
import com.ravvy.gymtrack.aluno.enums.TipoSexoBiologico;
import com.ravvy.gymtrack.avaliacao.entity.Avaliacao;
import com.ravvy.gymtrack.avaliacao.enums.TipoClassificacao;
import com.ravvy.gymtrack.avaliacao.repository.AvaliacaoRepository;
import com.ravvy.gymtrack.instituicao.entity.Instituicao;
import com.ravvy.gymtrack.instituicao.repository.InstituicaoRepository;
import com.ravvy.gymtrack.instituicao.service.InstituicaoService;
import com.ravvy.gymtrack.relatorio.dto.RelatorioEvolucaoInstitucionalResponse;
import com.ravvy.gymtrack.relatorio.dto.RelatorioMensalInstitucionalResponse;
import com.ravvy.gymtrack.relatorio.faixasEtaria.FaixaEtaria;
import com.ravvy.gymtrack.relatorio.service.RelatorioEvolucaoInstitucionalService;
import com.ravvy.gymtrack.shared.exception.RegraDeNegocioExeption;
import com.ravvy.gymtrack.testes.classificacao.ClassificacaoImcService;
import com.ravvy.gymtrack.testes.classificacao.ClassificacaoRceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RelatorioEvolucaoInstitucionalServiceTest {

    private static final Long ID_INSTITUICAO = 1L;

    @Mock
    private InstituicaoService instituicaoService;

    @Mock
    private InstituicaoRepository instituicaoRepository;

    @Mock
    private AvaliacaoRepository avaliacaoRepository;

    @Mock
    private ClassificacaoRceService classificacaoRceService;

    @Mock
    private ClassificacaoImcService classificacaoImcService;

    @InjectMocks
    private RelatorioEvolucaoInstitucionalService relatorioService;

    private Instituicao instituicao;

    @BeforeEach
    void configurar() {
        instituicao = new Instituicao();
        instituicao.setId(ID_INSTITUICAO);
        instituicao.setNome("Instituição Teste");
    }

    @Test
    void deveLancarExcecaoQuandoIdInstituicaoForNulo() {
        assertThrows(
                RegraDeNegocioExeption.class,
                () -> relatorioService.getRelatorioInstituicao(null)
        );

        verifyNoInteractions(
                instituicaoService,
                instituicaoRepository,
                avaliacaoRepository,
                classificacaoRceService,
                classificacaoImcService
        );
    }

    @Test
    void deveLancarExcecaoQuandoInstituicaoNaoTiverAvaliacoes() {
        prepararAvaliacoes();

        assertThrows(
                RegraDeNegocioExeption.class,
                () -> relatorioService.getRelatorioInstituicao(
                        ID_INSTITUICAO
                )
        );
    }

    @Test
    void deveGerarRelatorioComUmaAvaliacao() {
        prepararAvaliacoes(
                criarAvaliacao(
                        1L, 8, LocalDate.of(2026, 1, 10), 18.0, 0.40
                )
        );

        RelatorioEvolucaoInstitucionalResponse resposta =
                relatorioService.getRelatorioInstituicao(
                        ID_INSTITUICAO
                );

        assertEquals(ID_INSTITUICAO, resposta.idInstituicao());
        assertEquals("Instituição Teste", resposta.nomeInstituicao());
        assertEquals(1, resposta.quantidadeTotalAlunos());
        assertEquals(1, resposta.meses().size());
    }

    @Test
    void deveContarCadaAlunoUmaUnicaVezNoPeriodo() {
        prepararAvaliacoes(
                criarAvaliacao(
                        1L, 8, LocalDate.of(2026, 1, 5), 18.0, 0.40
                ),
                criarAvaliacao(
                        1L, 8, LocalDate.of(2026, 2, 5), 19.0, 0.42
                ),
                criarAvaliacao(
                        2L, 10, LocalDate.of(2026, 2, 8), 21.0, 0.51
                )
        );

        RelatorioEvolucaoInstitucionalResponse resposta =
                relatorioService.getRelatorioInstituicao(
                        ID_INSTITUICAO
                );

        assertEquals(2, resposta.quantidadeTotalAlunos());
    }

    @Test
    void deveUsarAvaliacaoMaisRecenteDoAlunoNoMesmoMes() {
        prepararAvaliacoes(
                criarAvaliacao(
                        1L, 8, LocalDate.of(2026, 1, 5), 18.0, 0.40
                ),
                criarAvaliacao(
                        1L, 8, LocalDate.of(2026, 1, 20), 22.0, 0.60
                )
        );

        RelatorioMensalInstitucionalResponse mes = obterPrimeiroMes();

        assertEquals(1, mes.quantidadeAlunos());
        assertEquals(22.0, mes.imcMedio(), 0.001);
        assertEquals(0.60, mes.rceMedio(), 0.001);
    }

    @Test
    void deveContarAlunoEmCadaMesEmQueFoiAvaliado() {
        prepararAvaliacoes(
                criarAvaliacao(
                        1L, 8, LocalDate.of(2026, 1, 5), 18.0, 0.40
                ),
                criarAvaliacao(
                        1L, 8, LocalDate.of(2026, 2, 5), 19.0, 0.42
                )
        );

        RelatorioEvolucaoInstitucionalResponse resposta =
                relatorioService.getRelatorioInstituicao(
                        ID_INSTITUICAO
                );

        List<RelatorioMensalInstitucionalResponse> meses =
                resposta.meses();

        assertEquals(1, resposta.quantidadeTotalAlunos());
        assertEquals(1, meses.get(0).quantidadeAlunos());
        assertEquals(1, meses.get(1).quantidadeAlunos());
    }

    @Test
    void deveOrdenarRelatoriosDoMaisAntigoParaOMaisRecente() {
        prepararAvaliacoes(
                criarAvaliacao(
                        1L, 8, LocalDate.of(2026, 1, 10), 18.0, 0.40
                ),
                criarAvaliacao(
                        2L, 9, LocalDate.of(2026, 2, 10), 19.0, 0.42
                ),
                criarAvaliacao(
                        3L, 10, LocalDate.of(2026, 3, 10), 21.0, 0.51
                )
        );

        List<RelatorioMensalInstitucionalResponse> meses =
                relatorioService
                        .getRelatorioInstituicao(ID_INSTITUICAO)
                        .meses();

        assertEquals(YearMonth.of(2026, 1), meses.get(0).mes());
        assertEquals(YearMonth.of(2026, 2), meses.get(1).mes());
        assertEquals(YearMonth.of(2026, 3), meses.get(2).mes());
    }

    @Test
    void deveCalcularMediaMensalDeImc() {
        prepararAvaliacoes(
                criarAvaliacao(
                        1L, 8, LocalDate.of(2026, 1, 5), 20.0, 0.40
                ),
                criarAvaliacao(
                        2L, 9, LocalDate.of(2026, 1, 10), 30.0, 0.60
                )
        );

        assertEquals(25.0, obterPrimeiroMes().imcMedio(), 0.001);
    }

    @Test
    void deveCalcularMediaMensalDeRce() {
        prepararAvaliacoes(
                criarAvaliacao(
                        1L, 8, LocalDate.of(2026, 1, 5), 20.0, 0.40
                ),
                criarAvaliacao(
                        2L, 9, LocalDate.of(2026, 1, 10), 30.0, 0.60
                )
        );

        assertEquals(0.50, obterPrimeiroMes().rceMedio(), 0.001);
    }

    @Test
    void deveClassificarMediaMensalDeRce() {
        prepararAvaliacoes(
                criarAvaliacao(
                        1L, 8, LocalDate.of(2026, 1, 5), 20.0, 0.40
                ),
                criarAvaliacao(
                        2L, 9, LocalDate.of(2026, 1, 10), 30.0, 0.60
                )
        );

        RelatorioMensalInstitucionalResponse mes = obterPrimeiroMes();

        assertEquals(
                TipoClassificacao.ZONA_DE_RISCO,
                mes.classificacaoRce()
        );

        verify(classificacaoRceService).classificar(0.50);
    }

    @Test
    void deveDistribuirAlunosNasFaixasEtariasCorretas() {
        prepararAvaliacoes(
                criarAvaliacao(
                        1L, 8, LocalDate.of(2026, 1, 5), 18.0, 0.40
                ),
                criarAvaliacao(
                        2L, 9, LocalDate.of(2026, 1, 6), 18.0, 0.40
                ),
                criarAvaliacao(
                        3L, 12, LocalDate.of(2026, 1, 7), 18.0, 0.40
                ),
                criarAvaliacao(
                        4L, 15, LocalDate.of(2026, 1, 8), 18.0, 0.40
                )
        );

        List<FaixaEtaria> faixas = obterPrimeiroMes().faixasEntarias();

        assertEquals(4, faixas.size());
        assertEquals(1, faixas.get(0).quantidadeAlunos());
        assertEquals(1, faixas.get(1).quantidadeAlunos());
        assertEquals(1, faixas.get(2).quantidadeAlunos());
        assertEquals(1, faixas.get(3).quantidadeAlunos());
    }

    @Test
    void deveCalcularPercentualDeAlunosPorFaixaEtaria() {
        prepararAvaliacoes(
                criarAvaliacao(
                        1L, 8, LocalDate.of(2026, 1, 5), 18.0, 0.40
                ),
                criarAvaliacao(
                        2L, 10, LocalDate.of(2026, 1, 6), 20.0, 0.45
                ),
                criarAvaliacao(
                        3L, 13, LocalDate.of(2026, 1, 7), 22.0, 0.55
                )
        );

        FaixaEtaria faixa = obterPrimeiroMes().faixasEntarias().get(0);

        assertEquals(100.0 / 3, faixa.percentualAlunos(), 0.001);
    }

    @Test
    void deveContarClassificacoesDeImcPorFaixaEtaria() {
        prepararAvaliacoes(
                criarAvaliacao(
                        1L, 8, LocalDate.of(2026, 1, 5), 18.0, 0.40
                ),
                criarAvaliacao(
                        2L, 8, LocalDate.of(2026, 1, 6), 19.0, 0.40
                ),
                criarAvaliacao(
                        3L, 8, LocalDate.of(2026, 1, 7), 25.0, 0.40
                )
        );

        FaixaEtaria faixa = obterPrimeiroMes().faixasEntarias().get(0);

        assertEquals(2, faixa.quantidadeAlunosSaudaveisImc());
        assertEquals(1, faixa.quantidadeAlunosRiscoImc());
        assertEquals(
                TipoClassificacao.ZONA_SAUDAVEL,
                faixa.classificacaoImc()
        );
    }

    @Test
    void deveContarClassificacoesDeRcePorFaixaEtaria() {
        prepararAvaliacoes(
                criarAvaliacao(
                        1L, 8, LocalDate.of(2026, 1, 5), 18.0, 0.40
                ),
                criarAvaliacao(
                        2L, 8, LocalDate.of(2026, 1, 6), 19.0, 0.40
                ),
                criarAvaliacao(
                        3L, 8, LocalDate.of(2026, 1, 7), 25.0, 0.60
                )
        );

        FaixaEtaria faixa = obterPrimeiroMes().faixasEntarias().get(0);

        assertEquals(2, faixa.quantidadeAlunosSaudaveisRce());
        assertEquals(1, faixa.quantidadeAlunosRiscoRce());
    }

    @Test
    void deveRetornarValoresVaziosParaFaixaSemAlunos() {
        prepararAvaliacoes(
                criarAvaliacao(
                        1L, 8, LocalDate.of(2026, 1, 5), 18.0, 0.40
                )
        );

        FaixaEtaria faixa = obterPrimeiroMes().faixasEntarias().get(1);

        assertEquals(0, faixa.quantidadeAlunos());
        assertEquals(0.0, faixa.percentualAlunos());
        assertNull(faixa.imcMedio());
        assertNull(faixa.rceMedio());
        assertNull(faixa.classificacaoImc());
        assertNull(faixa.classificacaoRce());
        assertEquals(0, faixa.quantidadeAlunosSaudaveisImc());
        assertEquals(0, faixa.quantidadeAlunosRiscoImc());
        assertEquals(0, faixa.quantidadeAlunosSaudaveisRce());
        assertEquals(0, faixa.quantidadeAlunosRiscoRce());
    }

    private void prepararAvaliacoes(Avaliacao... avaliacoes) {
        when(instituicaoService.buscarPorId(ID_INSTITUICAO))
                .thenReturn(instituicao);

        when(avaliacaoRepository
                .findByAlunoInstituicaoIdOrderByDataAvaliacaoAsc(
                        ID_INSTITUICAO
                ))
                .thenReturn(Arrays.asList(avaliacoes));

        if (avaliacoes.length > 0) {
            configurarClassificadores();
        }
    }

    private void configurarClassificadores() {
        when(classificacaoImcService.classificar(
                any(TipoSexoBiologico.class),
                anyInt(),
                anyDouble()
        )).thenAnswer(invocation -> {
            double imc = invocation.getArgument(2);

            return imc < 20
                    ? TipoClassificacao.ZONA_SAUDAVEL
                    : TipoClassificacao.ZONA_DE_RISCO;
        });

        when(classificacaoRceService.classificar(anyDouble()))
                .thenAnswer(invocation -> {
                    double rce = invocation.getArgument(0);

                    return rce < 0.50
                            ? TipoClassificacao.ZONA_SAUDAVEL
                            : TipoClassificacao.ZONA_DE_RISCO;
                });
    }

    private RelatorioMensalInstitucionalResponse obterPrimeiroMes() {
        return relatorioService
                .getRelatorioInstituicao(ID_INSTITUICAO)
                .meses()
                .getFirst();
    }

    private Avaliacao criarAvaliacao(
            Long idAluno,
            int idade,
            LocalDate data,
            double imc,
            double rce
    ) {
        Aluno aluno = new Aluno();
        aluno.setId(idAluno);

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setAluno(aluno);
        avaliacao.setIdade(idade);
        avaliacao.setDataAvaliacao(data);
        avaliacao.setImc(imc);
        avaliacao.setRce(rce);
        avaliacao.setSexo(TipoSexoBiologico.MASCULINO);

        return avaliacao;
    }
}