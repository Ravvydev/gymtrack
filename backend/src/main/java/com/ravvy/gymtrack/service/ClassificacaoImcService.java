package com.ravvy.gymtrack.service;

import com.ravvy.gymtrack.exception.RegraDeNegocioExeption;
import com.ravvy.gymtrack.util.FaixaIMC;
import com.ravvy.gymtrack.util.TipoClassificacao;
import com.ravvy.gymtrack.util.TipoSexoBiologico;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ClassificacaoImcService {

    private static final Map<TipoSexoBiologico,
            Map<Integer, FaixaIMC>> TABELA_IMC = new HashMap<>();

    static {

        Map<Integer, FaixaIMC> masculino = new HashMap<>();

        masculino.put(6, new FaixaIMC(17.7));
        masculino.put(7, new FaixaIMC(17.8));
        masculino.put(8, new FaixaIMC(19.2));
        masculino.put(9, new FaixaIMC(19.3));
        masculino.put(10, new FaixaIMC(20.7));
        masculino.put(11, new FaixaIMC(22.1));
        masculino.put(12, new FaixaIMC(22.2));
        masculino.put(13, new FaixaIMC(22.0));
        masculino.put(14, new FaixaIMC(22.2));
        masculino.put(15, new FaixaIMC(23.0));
        masculino.put(16, new FaixaIMC(24.0));
        masculino.put(17, new FaixaIMC(25.4));

        Map<Integer, FaixaIMC> feminino = new HashMap<>();

        feminino.put(6, new FaixaIMC(17.0));
        feminino.put(7, new FaixaIMC(17.1));
        feminino.put(8, new FaixaIMC(18.2));
        feminino.put(9, new FaixaIMC(19.1));
        feminino.put(10, new FaixaIMC(20.9));
        feminino.put(11, new FaixaIMC(22.3));
        feminino.put(12, new FaixaIMC(22.6));
        feminino.put(13, new FaixaIMC(22.0));
        feminino.put(14, new FaixaIMC(22.0));
        feminino.put(15, new FaixaIMC(22.4));
        feminino.put(16, new FaixaIMC(24.0));
        feminino.put(17, new FaixaIMC(24.0));

        TABELA_IMC.put(TipoSexoBiologico.MASCULINO, masculino);
        TABELA_IMC.put(TipoSexoBiologico.FEMININO, feminino);
    }

    public TipoClassificacao classificar(
            TipoSexoBiologico sexo,
            Integer idade,
            Double imc
    ) {

        if (sexo == null) {
            throw new RegraDeNegocioExeption(
                    "O sexo biológico não pode ser nulo."
            );
        }

        if (idade == null || idade < 6 || idade > 17) {
            throw new RegraDeNegocioExeption(
                    "A idade deve estar entre 6 e 17 anos."
            );
        }

        if (imc == null || imc <= 0) {
            throw new RegraDeNegocioExeption(
                    "O IMC deve ser maior que zero."
            );
        }

        Map<Integer, FaixaIMC> tabelaSexo =
                TABELA_IMC.get(sexo);

        if (tabelaSexo == null) {
            throw new RegraDeNegocioExeption(
                    "Sexo biológico inválido para classificação do IMC."
            );
        }

        FaixaIMC faixa = tabelaSexo.get(idade);

        if (faixa == null) {
            throw new RegraDeNegocioExeption(
                    "Não existe referência de IMC para "
                            + sexo
                            + ", idade "
                            + idade
            );
        }

        if (imc > faixa.getMaximo()) {
            return TipoClassificacao.ZONA_DE_RISCO;
        }

        return TipoClassificacao.ZONA_SAUDAVEL;
    }
}