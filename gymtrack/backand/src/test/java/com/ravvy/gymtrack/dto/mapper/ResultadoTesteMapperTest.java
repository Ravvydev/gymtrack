package com.ravvy.gymtrack.dto.mapper;

import com.ravvy.gymtrack.dto.ResultadoTesteResponse;
import com.ravvy.gymtrack.model.Avaliacao;
import com.ravvy.gymtrack.model.TesteRealizado;
import com.ravvy.gymtrack.service.ClassificacaoDesempenhoService;
import com.ravvy.gymtrack.service.ClassificacaoSaudeService;
import com.ravvy.gymtrack.util.TipoDesempenho;
import com.ravvy.gymtrack.util.TipoSaude;
import com.ravvy.gymtrack.util.TipoSexoBiologico;
import com.ravvy.gymtrack.util.TipoTesteFisico;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ResultadoTesteMapperTest {

    @Test
    void deveRetornarClassificacaoDeSaudeEDesempenho() {

        ClassificacaoDesempenhoService desempenhoService =
                mock(ClassificacaoDesempenhoService.class);

        ClassificacaoSaudeService saudeService =
                mock(ClassificacaoSaudeService.class);

        ResultadoTesteMapper mapper =
                new ResultadoTesteMapper(
                        desempenhoService,
                        saudeService
                );

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setIdade(12);
        avaliacao.setSexo(TipoSexoBiologico.MASCULINO);

        TesteRealizado teste = new TesteRealizado();
        teste.setAvaliacao(avaliacao);
        teste.setTipoTeste(TipoTesteFisico.CORRIDA_6_MINUTOS);
        teste.setResultadoObtido(1000.0);

        when(desempenhoService.classificar(
                TipoTesteFisico.CORRIDA_6_MINUTOS,
                TipoSexoBiologico.MASCULINO,
                12,
                1000.0
        )).thenReturn(TipoDesempenho.BOM);

        when(saudeService.classificar(
                TipoTesteFisico.CORRIDA_6_MINUTOS,
                TipoSexoBiologico.MASCULINO,
                12,
                1000.0
        )).thenReturn(TipoSaude.ZONA_SAUDAVEL);

        ResultadoTesteResponse response =
                mapper.toResponse(teste);

        assertEquals(
                TipoTesteFisico.CORRIDA_6_MINUTOS,
                response.tipoTeste()
        );

        assertEquals(
                1000.0,
                response.resultadoObtido()
        );

        assertEquals(
                TipoDesempenho.BOM,
                response.desempenho()
        );

        assertEquals(
                TipoSaude.ZONA_SAUDAVEL,
                response.saude()
        );
    }

    @Test
    void deveRetornarSaudeNulaQuandoTesteNaoPossuiClassificacaoDeSaude() {

        ClassificacaoDesempenhoService desempenhoService =
                mock(ClassificacaoDesempenhoService.class);

        ClassificacaoSaudeService saudeService =
                mock(ClassificacaoSaudeService.class);

        ResultadoTesteMapper mapper =
                new ResultadoTesteMapper(
                        desempenhoService,
                        saudeService
                );

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setIdade(12);
        avaliacao.setSexo(TipoSexoBiologico.MASCULINO);

        TesteRealizado teste = new TesteRealizado();
        teste.setAvaliacao(avaliacao);
        teste.setTipoTeste(TipoTesteFisico.SALTO_HORIZONTAL);
        teste.setResultadoObtido(160.0);

        when(desempenhoService.classificar(
                TipoTesteFisico.SALTO_HORIZONTAL,
                TipoSexoBiologico.MASCULINO,
                12,
                160.0
        )).thenReturn(TipoDesempenho.BOM);

        ResultadoTesteResponse response =
                mapper.toResponse(teste);

        assertEquals(
                TipoDesempenho.BOM,
                response.desempenho()
        );

        assertNull(response.saude());
    }
}