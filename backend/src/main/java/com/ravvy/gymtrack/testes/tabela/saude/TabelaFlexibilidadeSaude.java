package com.ravvy.gymtrack.testes.tabela.saude;

import com.ravvy.gymtrack.avaliacao.enums.DirecaoResultado;
import com.ravvy.gymtrack.testes.records.ReferenciaSaude;
import com.ravvy.gymtrack.avaliacao.enums.TipoSexoBiologico;
import com.ravvy.gymtrack.avaliacao.enums.TipoTesteFisico;

import java.util.List;

public class TabelaFlexibilidadeSaude {

    public static List<ReferenciaSaude> obter() {

        return List.of(

                new ReferenciaSaude(
                        6,
                        TipoSexoBiologico.MASCULINO,
                        TipoTesteFisico.FLEXIBILIDADE,
                        DirecaoResultado.MAIOR_MELHOR,
                        29.0
                ),
                new ReferenciaSaude(
                        6,
                        TipoSexoBiologico.FEMININO,
                        TipoTesteFisico.FLEXIBILIDADE,
                        DirecaoResultado.MAIOR_MELHOR,
                        40.5
                ),

                new ReferenciaSaude(
                        7,
                        TipoSexoBiologico.MASCULINO,
                        TipoTesteFisico.FLEXIBILIDADE,
                        DirecaoResultado.MAIOR_MELHOR,
                        29.0
                ),
                new ReferenciaSaude(
                        7,
                        TipoSexoBiologico.FEMININO,
                        TipoTesteFisico.FLEXIBILIDADE,
                        DirecaoResultado.MAIOR_MELHOR,
                        40.5
                ),

                new ReferenciaSaude(
                        8,
                        TipoSexoBiologico.MASCULINO,
                        TipoTesteFisico.FLEXIBILIDADE,
                        DirecaoResultado.MAIOR_MELHOR,
                        32.5
                ),
                new ReferenciaSaude(
                        8,
                        TipoSexoBiologico.FEMININO,
                        TipoTesteFisico.FLEXIBILIDADE,
                        DirecaoResultado.MAIOR_MELHOR,
                        39.5
                ),

                new ReferenciaSaude(
                        9,
                        TipoSexoBiologico.MASCULINO,
                        TipoTesteFisico.FLEXIBILIDADE,
                        DirecaoResultado.MAIOR_MELHOR,
                        29.0
                ),
                new ReferenciaSaude(
                        9,
                        TipoSexoBiologico.FEMININO,
                        TipoTesteFisico.FLEXIBILIDADE,
                        DirecaoResultado.MAIOR_MELHOR,
                        35.0
                ),

                new ReferenciaSaude(
                        10,
                        TipoSexoBiologico.MASCULINO,
                        TipoTesteFisico.FLEXIBILIDADE,
                        DirecaoResultado.MAIOR_MELHOR,
                        29.5
                ),
                new ReferenciaSaude(
                        10,
                        TipoSexoBiologico.FEMININO,
                        TipoTesteFisico.FLEXIBILIDADE,
                        DirecaoResultado.MAIOR_MELHOR,
                        36.5
                ),

                new ReferenciaSaude(
                        11,
                        TipoSexoBiologico.MASCULINO,
                        TipoTesteFisico.FLEXIBILIDADE,
                        DirecaoResultado.MAIOR_MELHOR,
                        29.5
                ),
                new ReferenciaSaude(
                        11,
                        TipoSexoBiologico.FEMININO,
                        TipoTesteFisico.FLEXIBILIDADE,
                        DirecaoResultado.MAIOR_MELHOR,
                        34.5
                ),

                new ReferenciaSaude(
                        12,
                        TipoSexoBiologico.MASCULINO,
                        TipoTesteFisico.FLEXIBILIDADE,
                        DirecaoResultado.MAIOR_MELHOR,
                        29.5
                ),
                new ReferenciaSaude(
                        12,
                        TipoSexoBiologico.FEMININO,
                        TipoTesteFisico.FLEXIBILIDADE,
                        DirecaoResultado.MAIOR_MELHOR,
                        39.5
                ),

                new ReferenciaSaude(
                        13,
                        TipoSexoBiologico.MASCULINO,
                        TipoTesteFisico.FLEXIBILIDADE,
                        DirecaoResultado.MAIOR_MELHOR,
                        26.5
                ),
                new ReferenciaSaude(
                        13,
                        TipoSexoBiologico.FEMININO,
                        TipoTesteFisico.FLEXIBILIDADE,
                        DirecaoResultado.MAIOR_MELHOR,
                        38.5
                ),

                new ReferenciaSaude(
                        14,
                        TipoSexoBiologico.MASCULINO,
                        TipoTesteFisico.FLEXIBILIDADE,
                        DirecaoResultado.MAIOR_MELHOR,
                        30.5
                ),
                new ReferenciaSaude(
                        14,
                        TipoSexoBiologico.FEMININO,
                        TipoTesteFisico.FLEXIBILIDADE,
                        DirecaoResultado.MAIOR_MELHOR,
                        38.5
                ),

                new ReferenciaSaude(
                        15,
                        TipoSexoBiologico.MASCULINO,
                        TipoTesteFisico.FLEXIBILIDADE,
                        DirecaoResultado.MAIOR_MELHOR,
                        31.0
                ),
                new ReferenciaSaude(
                        15,
                        TipoSexoBiologico.FEMININO,
                        TipoTesteFisico.FLEXIBILIDADE,
                        DirecaoResultado.MAIOR_MELHOR,
                        38.5
                ),

                new ReferenciaSaude(
                        16,
                        TipoSexoBiologico.MASCULINO,
                        TipoTesteFisico.FLEXIBILIDADE,
                        DirecaoResultado.MAIOR_MELHOR,
                        34.5
                ),
                new ReferenciaSaude(
                        16,
                        TipoSexoBiologico.FEMININO,
                        TipoTesteFisico.FLEXIBILIDADE,
                        DirecaoResultado.MAIOR_MELHOR,
                        39.5
                ),

                new ReferenciaSaude(
                        17,
                        TipoSexoBiologico.MASCULINO,
                        TipoTesteFisico.FLEXIBILIDADE,
                        DirecaoResultado.MAIOR_MELHOR,
                        34.0
                ),
                new ReferenciaSaude(
                        17,
                        TipoSexoBiologico.FEMININO,
                        TipoTesteFisico.FLEXIBILIDADE,
                        DirecaoResultado.MAIOR_MELHOR,
                        39.5
                )
        );
    }
}