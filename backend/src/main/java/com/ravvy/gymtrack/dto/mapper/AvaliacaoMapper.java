package com.ravvy.gymtrack.dto.mapper;

import com.ravvy.gymtrack.dto.AvaliacaoCreatedRequest;
import com.ravvy.gymtrack.dto.AvaliacaoResponse;
import com.ravvy.gymtrack.dto.AvaliacaoUploadRequest;
import com.ravvy.gymtrack.dto.ResultadoTesteResponse;
import com.ravvy.gymtrack.model.Aluno;
import com.ravvy.gymtrack.model.Avaliacao;
import com.ravvy.gymtrack.model.Professor;
import com.ravvy.gymtrack.model.TesteRealizado;
import com.ravvy.gymtrack.service.YearOldService;
import com.ravvy.gymtrack.util.TipoClassificacao;
import com.ravvy.gymtrack.util.TipoTesteFisico;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AvaliacaoMapper {

    private final ResultadoTesteMapper resultadoTesteMapper;

    public Avaliacao toEntity(
            AvaliacaoCreatedRequest request,
            Avaliacao avaliacao,
            Aluno aluno,
            Professor professor,
            Double imc,
            Double rce,
            TipoClassificacao classificacaoImc,
            TipoClassificacao classificacaoRce
    ) {

        avaliacao.setAluno(aluno);
        avaliacao.setProfessor(professor);
        avaliacao.setPeso(request.peso());
        avaliacao.setAltura(request.altura());
        avaliacao.setPerimetroCintura(request.perimetroCintura());
        avaliacao.setEnvergadura(request.envergadura());
        avaliacao.setSexo(aluno.getSexo());

        avaliacao.setIdade(
                YearOldService.calcularIdade(
                        aluno.getDataNascimento()
                )
        );

        avaliacao.setImc(imc);
        avaliacao.setRce(rce);
        avaliacao.setDataAvaliacao(LocalDate.now());
        avaliacao.setClassificacaoImc(classificacaoImc);
        avaliacao.setClassificacaoRce(classificacaoRce);

        List<TesteRealizado> testesRealizados =
                request.testes()
                        .stream()
                        .map(testeRequest -> {

                            TesteRealizado teste = new TesteRealizado();

                            teste.setAvaliacao(avaliacao);

                            teste.setTipoTeste(
                                    testeRequest.tipoTeste()
                            );

                            teste.setResultadoObtido(
                                    testeRequest.resultado()
                            );

                            teste.setUnidadeMedida(
                                    obterUnidadeMedida(
                                            testeRequest.tipoTeste()
                                    )
                            );

                            return teste;
                        })
                        .toList();

        avaliacao.getTestesRealizados()
                .addAll(testesRealizados);

        return avaliacao;
    }

    public AvaliacaoResponse toResponse(Avaliacao avaliacao) {

        List<ResultadoTesteResponse> testes =
                avaliacao.getTestesRealizados()
                        .stream()
                        .map(resultadoTesteMapper::toResponse)
                        .toList();

        return new AvaliacaoResponse(
                avaliacao.getId(),
                avaliacao.getAluno().getNome(),
                avaliacao.getIdade(),
                avaliacao.getSexo(),
                avaliacao.getDataAvaliacao(),
                avaliacao.getPeso(),
                avaliacao.getAltura(),
                avaliacao.getEnvergadura(),
                avaliacao.getPerimetroCintura(),
                avaliacao.getImc(),
                avaliacao.getClassificacaoImc(),
                avaliacao.getRce(),
                avaliacao.getClassificacaoRce(),
                avaliacao.getProfessor().getNome(),
                testes
        );
    }

    public void uploadAvaliacao(
            AvaliacaoUploadRequest request,
            Avaliacao avaliacao,
            Aluno aluno,
            Professor professor
    ) {

        avaliacao.setAluno(aluno);
        avaliacao.setProfessor(professor);

        avaliacao.setPeso(request.peso());
        avaliacao.setAltura(request.altura());
        avaliacao.setEnvergadura(request.envergadura());
        avaliacao.setPerimetroCintura(
                request.perimetroCintura()
        );

        avaliacao.setSexo(aluno.getSexo());

        avaliacao.setIdade(
                YearOldService.calcularIdade(
                        aluno.getDataNascimento()
                )
        );

        // Remove os testes antigos.
        // Como a relação possui orphanRemoval = true,
        // eles também serão removidos do banco.
        avaliacao.getTestesRealizados().clear();

        // Cria os testes atualizados.
        request.testes().forEach(testeRequest -> {

            TesteRealizado teste = new TesteRealizado();

            teste.setAvaliacao(avaliacao);

            teste.setTipoTeste(
                    testeRequest.tipoTeste()
            );

            teste.setResultadoObtido(
                    testeRequest.resultado()
            );

            teste.setUnidadeMedida(
                    obterUnidadeMedida(
                            testeRequest.tipoTeste()
                    )
            );

            avaliacao.getTestesRealizados().add(teste);
        });
    }

    private String obterUnidadeMedida(
            TipoTesteFisico tipoTeste
    ) {

        return switch (tipoTeste) {

            case CORRIDA_20_METROS,
                 QUADRADO_4X4_METROS -> "s";

            case CORRIDA_6_MINUTOS -> "m";

            case FLEXIBILIDADE,
                 SALTO_HORIZONTAL,
                 MEDICINE_BALL_2KG -> "cm";

            case ABDOMINAIS_1_MINUTO -> "repeticoes";
        };
    }
}