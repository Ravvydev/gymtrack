package com.ravvy.gymtrack.testes.mapper;

import com.ravvy.gymtrack.testes.dto.ResultadoTesteResponse;
import com.ravvy.gymtrack.avaliacao.entity.Avaliacao;
import com.ravvy.gymtrack.testes.entity.TesteRealizado;
import com.ravvy.gymtrack.testes.classificacao.ClassificacaoDesempenhoService;
import com.ravvy.gymtrack.testes.classificacao.ClassificacaoSaudeService;
import com.ravvy.gymtrack.avaliacao.enums.TipoDesempenho;
import com.ravvy.gymtrack.avaliacao.enums.TipoSaude;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResultadoTesteMapper {

    private final ClassificacaoDesempenhoService desempenhoService;
    private final ClassificacaoSaudeService saudeService;

    public ResultadoTesteResponse toResponse(TesteRealizado teste) {

        Avaliacao avaliacao = teste.getAvaliacao();

        TipoDesempenho desempenho = desempenhoService.classificar(
                teste.getTipoTeste(),
                avaliacao.getSexo(),
                avaliacao.getIdade(),
                teste.getResultadoObtido()
        );

        TipoSaude saude = null;

        switch (teste.getTipoTeste()) {

            case CORRIDA_20_METROS,
                 CORRIDA_6_MINUTOS,
                 FLEXIBILIDADE,
                 ABDOMINAIS_1_MINUTO,
                 MEDICINE_BALL_2KG ->

                    saude = saudeService.classificar(
                            teste.getTipoTeste(),
                            avaliacao.getSexo(),
                            avaliacao.getIdade(),
                            teste.getResultadoObtido()
                    );

            case SALTO_HORIZONTAL,
                 QUADRADO_4X4_METROS -> {
                // Esses testes não possuem classificação de saúde no PROESP-Br.
            }
        }

        return new ResultadoTesteResponse(
                teste.getTipoTeste(),
                teste.getResultadoObtido(),
                teste.getUnidadeMedida(),
                desempenho,
                saude
        );
    }
}