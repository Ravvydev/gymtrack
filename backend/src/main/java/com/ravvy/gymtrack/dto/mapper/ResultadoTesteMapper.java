package com.ravvy.gymtrack.dto.mapper;

import com.ravvy.gymtrack.dto.ResultadoTesteResponse;
import com.ravvy.gymtrack.model.Avaliacao;
import com.ravvy.gymtrack.model.TesteRealizado;
import com.ravvy.gymtrack.service.ClassificacaoDesempenhoService;
import com.ravvy.gymtrack.service.ClassificacaoSaudeService;
import com.ravvy.gymtrack.util.TipoDesempenho;
import com.ravvy.gymtrack.util.TipoSaude;
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