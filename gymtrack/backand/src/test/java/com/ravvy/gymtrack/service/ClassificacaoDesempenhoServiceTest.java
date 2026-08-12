package com.ravvy.gymtrack.service;

import com.ravvy.gymtrack.exception.RegraDeNegocioExeption;
import com.ravvy.gymtrack.util.TipoDesempenho;
import com.ravvy.gymtrack.util.TipoSexoBiologico;
import com.ravvy.gymtrack.util.TipoTesteFisico;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ClassificacaoDesempenhoServiceTest {

    private final ClassificacaoDesempenhoService service =
            new ClassificacaoDesempenhoService();

    @Test
    void deveClassificarCorrida6MinutosComoFraco() {

        TipoDesempenho resultado = service.classificar(
                TipoTesteFisico.CORRIDA_6_MINUTOS,
                TipoSexoBiologico.MASCULINO,
                12,
                800
        );

        assertEquals(
                TipoDesempenho.FRACO,
                resultado
        );
    }

    @Test
    void deveClassificarCorrida6MinutosComoRazoavel() {

        TipoDesempenho resultado = service.classificar(
                TipoTesteFisico.CORRIDA_6_MINUTOS,
                TipoSexoBiologico.MASCULINO,
                12,
                860
        );

        assertEquals(
                TipoDesempenho.RAZOAVEL,
                resultado
        );
    }

    @Test
    void deveClassificarCorrida6MinutosComoBom() {

        TipoDesempenho resultado = service.classificar(
                TipoTesteFisico.CORRIDA_6_MINUTOS,
                TipoSexoBiologico.MASCULINO,
                12,
                1000
        );

        assertEquals(
                TipoDesempenho.BOM,
                resultado
        );
    }

    @Test
    void deveClassificarCorrida6MinutosComoMuitoBom() {

        TipoDesempenho resultado = service.classificar(
                TipoTesteFisico.CORRIDA_6_MINUTOS,
                TipoSexoBiologico.MASCULINO,
                12,
                1200
        );

        assertEquals(
                TipoDesempenho.MUITO_BOM,
                resultado
        );
    }

    @Test
    void deveClassificarCorrida6MinutosComoExcelencia() {

        TipoDesempenho resultado = service.classificar(
                TipoTesteFisico.CORRIDA_6_MINUTOS,
                TipoSexoBiologico.MASCULINO,
                12,
                1400
        );

        assertEquals(
                TipoDesempenho.EXCELENCIA,
                resultado
        );
    }

    @Test
    void deveClassificarFlexibilidadeComoFraco() {

        TipoDesempenho resultado = service.classificar(
                TipoTesteFisico.FLEXIBILIDADE,
                TipoSexoBiologico.MASCULINO,
                12,
                25.0
        );

        assertEquals(
                TipoDesempenho.FRACO,
                resultado
        );
    }

    @Test
    void deveClassificarFlexibilidadeComoRazoavel() {

        TipoDesempenho resultado = service.classificar(
                TipoTesteFisico.FLEXIBILIDADE,
                TipoSexoBiologico.MASCULINO,
                12,
                30.0
        );

        assertEquals(
                TipoDesempenho.RAZOAVEL,
                resultado
        );
    }

    @Test
    void deveClassificarFlexibilidadeComoBom() {

        TipoDesempenho resultado = service.classificar(
                TipoTesteFisico.FLEXIBILIDADE,
                TipoSexoBiologico.MASCULINO,
                12,
                40.0
        );

        assertEquals(
                TipoDesempenho.BOM,
                resultado
        );
    }

    @Test
    void deveClassificarFlexibilidadeComoMuitoBom() {

        TipoDesempenho resultado = service.classificar(
                TipoTesteFisico.FLEXIBILIDADE,
                TipoSexoBiologico.MASCULINO,
                12,
                50.0
        );

        assertEquals(
                TipoDesempenho.MUITO_BOM,
                resultado
        );
    }

    @Test
    void deveClassificarFlexibilidadeComoExcelencia() {

        TipoDesempenho resultado = service.classificar(
                TipoTesteFisico.FLEXIBILIDADE,
                TipoSexoBiologico.MASCULINO,
                12,
                60.0
        );

        assertEquals(
                TipoDesempenho.EXCELENCIA,
                resultado
        );
    }

    @Test
    void deveClassificarAbdominaisComoFraco() {

        TipoDesempenho resultado = service.classificar(
                TipoTesteFisico.ABDOMINAIS_1_MINUTO,
                TipoSexoBiologico.MASCULINO,
                12,
                25
        );

        assertEquals(
                TipoDesempenho.FRACO,
                resultado
        );
    }

    @Test
    void deveClassificarAbdominaisComoRazoavel() {

        TipoDesempenho resultado = service.classificar(
                TipoTesteFisico.ABDOMINAIS_1_MINUTO,
                TipoSexoBiologico.MASCULINO,
                12,
                30
        );

        assertEquals(
                TipoDesempenho.RAZOAVEL,
                resultado
        );
    }

    @Test
    void deveClassificarAbdominaisComoBom() {

        TipoDesempenho resultado = service.classificar(
                TipoTesteFisico.ABDOMINAIS_1_MINUTO,
                TipoSexoBiologico.MASCULINO,
                12,
                37
        );

        assertEquals(
                TipoDesempenho.BOM,
                resultado
        );
    }

    @Test
    void deveClassificarAbdominaisComoMuitoBom() {

        TipoDesempenho resultado = service.classificar(
                TipoTesteFisico.ABDOMINAIS_1_MINUTO,
                TipoSexoBiologico.MASCULINO,
                12,
                45
        );

        assertEquals(
                TipoDesempenho.MUITO_BOM,
                resultado
        );
    }

    @Test
    void deveClassificarAbdominaisComoExcelencia() {

        TipoDesempenho resultado = service.classificar(
                TipoTesteFisico.ABDOMINAIS_1_MINUTO,
                TipoSexoBiologico.MASCULINO,
                12,
                55
        );

        assertEquals(
                TipoDesempenho.EXCELENCIA,
                resultado
        );
    }

    @Test
    void deveClassificarMedicineBallComoFraco() {

        TipoDesempenho resultado = service.classificar(
                TipoTesteFisico.MEDICINE_BALL_2KG,
                TipoSexoBiologico.MASCULINO,
                12,
                200.0
        );

        assertEquals(TipoDesempenho.FRACO, resultado);
    }

    @Test
    void deveClassificarMedicineBallComoRazoavel() {

        TipoDesempenho resultado = service.classificar(
                TipoTesteFisico.MEDICINE_BALL_2KG,
                TipoSexoBiologico.MASCULINO,
                12,
                270.0
        );

        assertEquals(TipoDesempenho.RAZOAVEL, resultado);
    }

    @Test
    void deveClassificarMedicineBallComoBom() {

        TipoDesempenho resultado = service.classificar(
                TipoTesteFisico.MEDICINE_BALL_2KG,
                TipoSexoBiologico.MASCULINO,
                12,
                300.0
        );

        assertEquals(TipoDesempenho.BOM, resultado);
    }

    @Test
    void deveClassificarMedicineBallComoMuitoBom() {

        TipoDesempenho resultado = service.classificar(
                TipoTesteFisico.MEDICINE_BALL_2KG,
                TipoSexoBiologico.MASCULINO,
                12,
                350.0
        );

        assertEquals(TipoDesempenho.MUITO_BOM, resultado);
    }

    @Test
    void deveClassificarMedicineBallComoExcelencia() {

        TipoDesempenho resultado = service.classificar(
                TipoTesteFisico.MEDICINE_BALL_2KG,
                TipoSexoBiologico.MASCULINO,
                12,
                450.0
        );

        assertEquals(TipoDesempenho.EXCELENCIA, resultado);
    }

    @Test
    void deveClassificarSaltoHorizontalComoFraco() {

        TipoDesempenho resultado = service.classificar(
                TipoTesteFisico.SALTO_HORIZONTAL,
                TipoSexoBiologico.MASCULINO,
                12,
                130.0
        );

        assertEquals(TipoDesempenho.FRACO, resultado);
    }

    @Test
    void deveClassificarSaltoHorizontalComoRazoavel() {

        TipoDesempenho resultado = service.classificar(
                TipoTesteFisico.SALTO_HORIZONTAL,
                TipoSexoBiologico.MASCULINO,
                12,
                150.0
        );

        assertEquals(TipoDesempenho.RAZOAVEL, resultado);
    }

    @Test
    void deveClassificarSaltoHorizontalComoBom() {

        TipoDesempenho resultado = service.classificar(
                TipoTesteFisico.SALTO_HORIZONTAL,
                TipoSexoBiologico.MASCULINO,
                12,
                160.0
        );

        assertEquals(TipoDesempenho.BOM, resultado);
    }

    @Test
    void deveClassificarSaltoHorizontalComoMuitoBom() {

        TipoDesempenho resultado = service.classificar(
                TipoTesteFisico.SALTO_HORIZONTAL,
                TipoSexoBiologico.MASCULINO,
                12,
                180.0
        );

        assertEquals(TipoDesempenho.MUITO_BOM, resultado);
    }

    @Test
    void deveClassificarSaltoHorizontalComoExcelencia() {

        TipoDesempenho resultado = service.classificar(
                TipoTesteFisico.SALTO_HORIZONTAL,
                TipoSexoBiologico.MASCULINO,
                12,
                210.0
        );

        assertEquals(TipoDesempenho.EXCELENCIA, resultado);
    }

    @Test
    void deveClassificarQuadrado4x4ComoExcelencia() {

        TipoDesempenho resultado = service.classificar(
                TipoTesteFisico.QUADRADO_4X4_METROS,
                TipoSexoBiologico.MASCULINO,
                12,
                5.00
        );

        assertEquals(TipoDesempenho.EXCELENCIA, resultado);
    }

    @Test
    void deveClassificarQuadrado4x4ComoMuitoBom() {

        TipoDesempenho resultado = service.classificar(
                TipoTesteFisico.QUADRADO_4X4_METROS,
                TipoSexoBiologico.MASCULINO,
                12,
                5.50
        );

        assertEquals(TipoDesempenho.MUITO_BOM, resultado);
    }

    @Test
    void deveClassificarQuadrado4x4ComoBom() {

        TipoDesempenho resultado = service.classificar(
                TipoTesteFisico.QUADRADO_4X4_METROS,
                TipoSexoBiologico.MASCULINO,
                12,
                6.20
        );

        assertEquals(TipoDesempenho.BOM, resultado);
    }

    @Test
    void deveClassificarQuadrado4x4ComoRazoavel() {

        TipoDesempenho resultado = service.classificar(
                TipoTesteFisico.QUADRADO_4X4_METROS,
                TipoSexoBiologico.MASCULINO,
                12,
                6.60
        );

        assertEquals(TipoDesempenho.RAZOAVEL, resultado);
    }

    @Test
    void deveClassificarQuadrado4x4ComoFraco() {

        TipoDesempenho resultado = service.classificar(
                TipoTesteFisico.QUADRADO_4X4_METROS,
                TipoSexoBiologico.MASCULINO,
                12,
                7.00
        );

        assertEquals(TipoDesempenho.FRACO, resultado);
    }

    @Test
    void deveLancarRegraDeNegocioExeptionQuandoNaoExisteReferenciaDeDesempenho() {

        assertThrows(
                RegraDeNegocioExeption.class,
                () -> service.classificar(
                        TipoTesteFisico.CORRIDA_20_METROS,
                        TipoSexoBiologico.MASCULINO,
                        18,
                        3.50
                )
        );
    }

    @Test
    void deveLancarExcecaoQuandoTipoTesteForNulo() {

        assertThrows(
                RegraDeNegocioExeption.class,
                () -> service.classificar(
                        null,
                        TipoSexoBiologico.MASCULINO,
                        12,
                        3.50
                )
        );
    }

    @Test
    void deveLancarExcecaoQuandoSexoForNulo() {

        assertThrows(
                RegraDeNegocioExeption.class,
                () -> service.classificar(
                        TipoTesteFisico.CORRIDA_20_METROS,
                        null,
                        12,
                        3.50
                )
        );
    }

    @Test
    void deveLancarExcecaoQuandoIdadeForInvalida() {

        assertThrows(
                RegraDeNegocioExeption.class,
                () -> service.classificar(
                        TipoTesteFisico.CORRIDA_20_METROS,
                        TipoSexoBiologico.MASCULINO,
                        5,
                        3.50
                )
        );
    }

    @Test
    void deveLancarExcecaoQuandoResultadoForNegativo() {

        assertThrows(
                RegraDeNegocioExeption.class,
                () -> service.classificar(
                        TipoTesteFisico.CORRIDA_20_METROS,
                        TipoSexoBiologico.MASCULINO,
                        12,
                        -1.0
                )
        );
    }
}