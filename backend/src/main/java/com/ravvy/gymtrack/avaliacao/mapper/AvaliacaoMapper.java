package com.ravvy.gymtrack.avaliacao.mapper;

import com.ravvy.gymtrack.avaliacao.dto.AvaliacaoCreatedRequest;
import com.ravvy.gymtrack.avaliacao.dto.AvaliacaoResponse;
import com.ravvy.gymtrack.avaliacao.dto.AvaliacaoUploadRequest;
import com.ravvy.gymtrack.aluno.entity.Aluno;
import com.ravvy.gymtrack.avaliacao.entity.Avaliacao;
import com.ravvy.gymtrack.testes.mapper.ResultadoTesteMapper;
import com.ravvy.gymtrack.professor.entity.Professor;
import com.ravvy.gymtrack.testes.entity.TesteRealizado;
import com.ravvy.gymtrack.shared.validacao.YearOldService;
import com.ravvy.gymtrack.avaliacao.enums.TipoClassificacao;
import com.ravvy.gymtrack.avaliacao.enums.TipoTesteFisico;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDate;
import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = ResultadoTesteMapper.class,
        imports = {
                YearOldService.class,
                LocalDate.class,
        }
)
public interface AvaliacaoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "testesRealizados", ignore = true)
    @Mapping(source = "aluno", target = "aluno")
    @Mapping(source = "professor", target = "professor")
    @Mapping(source = "imc", target = "imc")
    @Mapping(source = "rce", target = "rce")
    @Mapping(source = "classificacaoImc", target = "classificacaoImc")
    @Mapping(source = "classificacaoRce", target = "classificacaoRce")
    @Mapping(source = "aluno.sexo", target = "sexo")
    @Mapping(
            target = "idade",
            expression = "java(YearOldService.calcularIdade(aluno.getDataNascimento()))"
    )
    @Mapping(
            target = "dataAvaliacao",
            expression = "java(LocalDate.now())"
    )
    @Mapping(source = "request.peso", target = "peso")
    @Mapping(source = "request.altura", target = "altura")
    @Mapping(source = "request.envergadura", target = "envergadura")
    @Mapping(source = "request.perimetroCintura", target = "perimetroCintura")
    Avaliacao toEntity(
            AvaliacaoCreatedRequest request,
            Aluno aluno,
            Professor professor,
            Double imc,
            Double rce,
            TipoClassificacao classificacaoImc,
            TipoClassificacao classificacaoRce
    );

    @Mapping(source = "aluno.nome", target = "nomeAluno")
    @Mapping(source = "professor.nome", target = "nomeProfessor")
    @Mapping(source = "idade", target = "idadeAluno")
    @Mapping(source = "sexo", target = "sexoAluno")
    AvaliacaoResponse toResponse(Avaliacao avaliacao);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataAvaliacao", ignore = true)
    @Mapping(target = "testesRealizados", ignore = true)
    @Mapping(source = "aluno", target = "aluno")
    @Mapping(source = "professor", target = "professor")
    @Mapping(source = "imc", target = "imc")
    @Mapping(source = "rce", target = "rce")
    @Mapping(source = "classificacaoImc", target = "classificacaoImc")
    @Mapping(source = "classificacaoRce", target = "classificacaoRce")
    @Mapping(source = "aluno.sexo", target = "sexo")
    @Mapping(source = "request.peso", target = "peso")
    @Mapping(source = "request.altura", target = "altura")
    @Mapping(source = "request.envergadura", target = "envergadura")
    @Mapping(source = "request.perimetroCintura", target = "perimetroCintura")
    @Mapping(
            target = "idade",
            expression = "java(YearOldService.calcularIdade(aluno.getDataNascimento()))"
    )
    void uploadAvaliacao(
            AvaliacaoUploadRequest request,
            @MappingTarget Avaliacao avaliacao,
            Aluno aluno,
            Professor professor,
            Double imc,
            Double rce,
            TipoClassificacao classificacaoImc,
            TipoClassificacao classificacaoRce
    );

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

    @AfterMapping
    default void adicionarTestes(
            AvaliacaoCreatedRequest request,
            @MappingTarget Avaliacao avaliacao
    ) {
        List<TesteRealizado> testesRealizados =
                request.testesRealizados()
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
    }

    @AfterMapping
    default void uploadTestesRealizados(
            AvaliacaoUploadRequest request,
            @MappingTarget Avaliacao avaliacao
    ) {
        // Remove os testes antigos.
        // Como a relação possui orphanRemoval = true,
        // eles também serão removidos do banco.
        avaliacao.getTestesRealizados().clear();

        // Cria os testes atualizados.
        request.testesRealizados().forEach(
                testeRequest -> {

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
                }
        );
    }

}