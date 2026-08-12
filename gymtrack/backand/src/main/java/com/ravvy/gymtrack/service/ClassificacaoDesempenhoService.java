package com.ravvy.gymtrack.service;

import com.ravvy.gymtrack.exception.RegraDeNegocioExeption;
import com.ravvy.gymtrack.service.tabela.desempenho.*;
import com.ravvy.gymtrack.util.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassificacaoDesempenhoService {

    public TipoDesempenho classificar(
            TipoTesteFisico tipoTeste,
            TipoSexoBiologico sexo,
            int idade,
            double resultado
    ) {

        if (tipoTeste == null) {
            throw new RegraDeNegocioExeption(
                    "O tipo de teste não pode ser nulo."
            );
        }

        if (sexo == null) {
            throw new RegraDeNegocioExeption(
                    "O sexo biológico não pode ser nulo."
            );
        }

        if (idade < 6 || idade > 17) {
            throw new RegraDeNegocioExeption(
                    "A idade deve estar entre 6 e 17 anos."
            );
        }

        if (resultado < 0) {
            throw new RegraDeNegocioExeption(
                    "O resultado do teste não pode ser negativo."
            );
        }

        ReferenciaDesempenho referencia =
                buscarReferencia(tipoTeste, sexo, idade);

        return classificarResultado(resultado, referencia);
    }

    private TipoDesempenho classificarResultado(
            double resultado,
            ReferenciaDesempenho referencia
    ) {

        if (referencia.direcao() == DirecaoResultado.MENOR_MELHOR) {

            if (resultado <= referencia.excelencia()) {
                return TipoDesempenho.EXCELENCIA;
            }

            if (resultado <= referencia.muitoBom()) {
                return TipoDesempenho.MUITO_BOM;
            }

            if (resultado <= referencia.bom()) {
                return TipoDesempenho.BOM;
            }

            if (resultado <= referencia.razoavel()) {
                return TipoDesempenho.RAZOAVEL;
            }

            return TipoDesempenho.FRACO;
        }

        if (resultado >= referencia.excelencia()) {
            return TipoDesempenho.EXCELENCIA;
        }

        if (resultado >= referencia.muitoBom()) {
            return TipoDesempenho.MUITO_BOM;
        }

        if (resultado >= referencia.bom()) {
            return TipoDesempenho.BOM;
        }

        if (resultado >= referencia.razoavel()) {
            return TipoDesempenho.RAZOAVEL;
        }

        return TipoDesempenho.FRACO;
    }

    private ReferenciaDesempenho buscarReferencia(
            TipoTesteFisico tipoTeste,
            TipoSexoBiologico sexo,
            int idade
    ) {

        List<ReferenciaDesempenho> referencias;

        switch (tipoTeste) {

            case CORRIDA_20_METROS -> referencias = TabelaCorrida20Metros.obter();

            case CORRIDA_6_MINUTOS -> referencias = TabelaCorrida6Minutos.obter();

            case FLEXIBILIDADE -> referencias = TabelaFlexibilidade.obter();

            case ABDOMINAIS_1_MINUTO -> referencias = TabelaAbdominais1Minuto.obter();

            case MEDICINE_BALL_2KG -> referencias = TabelaMedicineBall2Kg.obter();

            case SALTO_HORIZONTAL -> referencias = TabelaSaltoHorizontal.obter();

            case QUADRADO_4X4_METROS -> referencias = TabelaQuadrado4x4Metros.obter();

            default -> throw new RegraDeNegocioExeption(
                    "Tabela de desempenho não cadastrada para o teste: "
                            + tipoTeste
            );
        }

        return referencias.stream()
                .filter(referencia ->
                        referencia.tipoTeste() == tipoTeste
                                && referencia.sexo() == sexo
                                && referencia.idade() == idade
                )
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioExeption(
                        "Não existe referência de desempenho para "
                                + tipoTeste
                                + ", sexo "
                                + sexo
                                + ", idade "
                                + idade
                ));
    }
}