package com.ravvy.gymtrack.service.tabela.desempenho;

import com.ravvy.gymtrack.util.DirecaoResultado;
import com.ravvy.gymtrack.util.ReferenciaDesempenho;
import com.ravvy.gymtrack.util.TipoSexoBiologico;
import com.ravvy.gymtrack.util.TipoTesteFisico;

import java.util.List;

public class TabelaMedicineBall2Kg {

    public static List<ReferenciaDesempenho> obter() {

        return List.of(

                // MASCULINO

                new ReferenciaDesempenho(6, TipoSexoBiologico.MASCULINO, TipoTesteFisico.MEDICINE_BALL_2KG, DirecaoResultado.MAIOR_MELHOR, 249.0, 180.4, 155.0, 136.2),
                new ReferenciaDesempenho(7, TipoSexoBiologico.MASCULINO, TipoTesteFisico.MEDICINE_BALL_2KG, DirecaoResultado.MAIOR_MELHOR, 261.4, 201.4, 175.6, 154.9),
                new ReferenciaDesempenho(8, TipoSexoBiologico.MASCULINO, TipoTesteFisico.MEDICINE_BALL_2KG, DirecaoResultado.MAIOR_MELHOR, 284.3, 223.3, 195.9, 173.4),
                new ReferenciaDesempenho(9, TipoSexoBiologico.MASCULINO, TipoTesteFisico.MEDICINE_BALL_2KG, DirecaoResultado.MAIOR_MELHOR, 315.3, 247.0, 216.8, 192.2),
                new ReferenciaDesempenho(10, TipoSexoBiologico.MASCULINO, TipoTesteFisico.MEDICINE_BALL_2KG, DirecaoResultado.MAIOR_MELHOR, 345.4, 268.8, 235.7, 209.2),
                new ReferenciaDesempenho(11, TipoSexoBiologico.MASCULINO, TipoTesteFisico.MEDICINE_BALL_2KG, DirecaoResultado.MAIOR_MELHOR, 376.8, 295.1, 259.2, 230.1),
                new ReferenciaDesempenho(12, TipoSexoBiologico.MASCULINO, TipoTesteFisico.MEDICINE_BALL_2KG, DirecaoResultado.MAIOR_MELHOR, 416.2, 327.4, 287.7, 255.2),
                new ReferenciaDesempenho(13, TipoSexoBiologico.MASCULINO, TipoTesteFisico.MEDICINE_BALL_2KG, DirecaoResultado.MAIOR_MELHOR, 479.7, 380.0, 334.0, 295.6),
                new ReferenciaDesempenho(14, TipoSexoBiologico.MASCULINO, TipoTesteFisico.MEDICINE_BALL_2KG, DirecaoResultado.MAIOR_MELHOR, 554.5, 446.5, 394.0, 348.5),
                new ReferenciaDesempenho(15, TipoSexoBiologico.MASCULINO, TipoTesteFisico.MEDICINE_BALL_2KG, DirecaoResultado.MAIOR_MELHOR, 623.5, 513.0, 456.1, 405.1),
                new ReferenciaDesempenho(16, TipoSexoBiologico.MASCULINO, TipoTesteFisico.MEDICINE_BALL_2KG, DirecaoResultado.MAIOR_MELHOR, 670.9, 560.1, 501.6, 448.3),
                new ReferenciaDesempenho(17, TipoSexoBiologico.MASCULINO, TipoTesteFisico.MEDICINE_BALL_2KG, DirecaoResultado.MAIOR_MELHOR, 710.4, 600.2, 541.2, 486.8),

                // FEMININO

                new ReferenciaDesempenho(6, TipoSexoBiologico.FEMININO, TipoTesteFisico.MEDICINE_BALL_2KG, DirecaoResultado.MAIOR_MELHOR, 214.9, 167.5, 146.7, 129.7),
                new ReferenciaDesempenho(7, TipoSexoBiologico.FEMININO, TipoTesteFisico.MEDICINE_BALL_2KG, DirecaoResultado.MAIOR_MELHOR, 230.5, 182.1, 160.0, 141.7),
                new ReferenciaDesempenho(8, TipoSexoBiologico.FEMININO, TipoTesteFisico.MEDICINE_BALL_2KG, DirecaoResultado.MAIOR_MELHOR, 252.2, 200.4, 176.5, 156.6),
                new ReferenciaDesempenho(9, TipoSexoBiologico.FEMININO, TipoTesteFisico.MEDICINE_BALL_2KG, DirecaoResultado.MAIOR_MELHOR, 279.6, 222.2, 195.9, 174.1),
                new ReferenciaDesempenho(10, TipoSexoBiologico.FEMININO, TipoTesteFisico.MEDICINE_BALL_2KG, DirecaoResultado.MAIOR_MELHOR, 308.1, 244.4, 215.6, 191.9),
                new ReferenciaDesempenho(11, TipoSexoBiologico.FEMININO, TipoTesteFisico.MEDICINE_BALL_2KG, DirecaoResultado.MAIOR_MELHOR, 341.9, 271.9, 240.3, 214.3),
                new ReferenciaDesempenho(12, TipoSexoBiologico.FEMININO, TipoTesteFisico.MEDICINE_BALL_2KG, DirecaoResultado.MAIOR_MELHOR, 372.2, 299.0, 265.1, 236.8),
                new ReferenciaDesempenho(13, TipoSexoBiologico.FEMININO, TipoTesteFisico.MEDICINE_BALL_2KG, DirecaoResultado.MAIOR_MELHOR, 403.5, 328.3, 292.2, 261.3),
                new ReferenciaDesempenho(14, TipoSexoBiologico.FEMININO, TipoTesteFisico.MEDICINE_BALL_2KG, DirecaoResultado.MAIOR_MELHOR, 431.8, 354.5, 316.6, 283.5),
                new ReferenciaDesempenho(15, TipoSexoBiologico.FEMININO, TipoTesteFisico.MEDICINE_BALL_2KG, DirecaoResultado.MAIOR_MELHOR, 452.9, 373.5, 334.2, 299.9),
                new ReferenciaDesempenho(16, TipoSexoBiologico.FEMININO, TipoTesteFisico.MEDICINE_BALL_2KG, DirecaoResultado.MAIOR_MELHOR, 468.1, 385.1, 344.7, 309.7),
                new ReferenciaDesempenho(17, TipoSexoBiologico.FEMININO, TipoTesteFisico.MEDICINE_BALL_2KG, DirecaoResultado.MAIOR_MELHOR, 484.1, 395.6, 353.8, 318.4)
        );
    }
}