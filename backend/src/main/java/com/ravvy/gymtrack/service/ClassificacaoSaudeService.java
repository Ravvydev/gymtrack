package com.ravvy.gymtrack.service;

import com.ravvy.gymtrack.exception.RegraDeNegocioExeption;
import com.ravvy.gymtrack.service.tabela.saude.*;
import com.ravvy.gymtrack.util.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassificacaoSaudeService {

    public TipoSaude classificar(
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

        ReferenciaSaude referencia =
                buscarReferencia(tipoTeste, sexo, idade);

        return classificarResultado(resultado, referencia);
    }

    private ReferenciaSaude buscarReferencia(
            TipoTesteFisico tipoTeste,
            TipoSexoBiologico sexo,
            int idade
    ) {

        List<ReferenciaSaude> referencias;

        switch (tipoTeste) {

            case CORRIDA_20_METROS -> referencias = TabelaCorrida20MetrosSaude.obter();

            case CORRIDA_6_MINUTOS -> referencias = TabelaCorrida6MinutosSaude.obter();

            case FLEXIBILIDADE -> referencias = TabelaFlexibilidadeSaude.obter();

            case ABDOMINAIS_1_MINUTO -> referencias = TabelaAbdominais1MinutoSaude.obter();

            case MEDICINE_BALL_2KG -> referencias = TabelaMedicineBall2KgSaude.obter();

            default -> throw new RegraDeNegocioExeption(
                    "Tabela de saúde não cadastrada para o teste: "
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
                        "Não existe referência de saúde para "
                                + tipoTeste
                                + ", sexo "
                                + sexo
                                + ", idade "
                                + idade
                ));
    }

    private TipoSaude classificarResultado(
            double resultado,
            ReferenciaSaude referencia
    ) {

        if (referencia.direcao() == DirecaoResultado.MENOR_MELHOR) {

            if (resultado <= referencia.pontoCorte()) {
                return TipoSaude.ZONA_SAUDAVEL;
            }

            return TipoSaude.ZONA_DE_RISCO;
        }

        if (resultado >= referencia.pontoCorte()) {
            return TipoSaude.ZONA_SAUDAVEL;
        }

        return TipoSaude.ZONA_DE_RISCO;
    }
}