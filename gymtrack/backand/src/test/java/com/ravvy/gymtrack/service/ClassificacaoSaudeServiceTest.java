package com.ravvy.gymtrack.service;

import com.ravvy.gymtrack.exception.RegraDeNegocioExeption;
import com.ravvy.gymtrack.util.TipoSaude;
import com.ravvy.gymtrack.util.TipoSexoBiologico;
import com.ravvy.gymtrack.util.TipoTesteFisico;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ClassificacaoSaudeServiceTest {

    private final ClassificacaoSaudeService service =
            new ClassificacaoSaudeService();

    @Test
    void deveClassificarCorrida20MetrosComoZonaSaudavel() {

        TipoSaude resultado = service.classificar(
                TipoTesteFisico.CORRIDA_20_METROS,
                TipoSexoBiologico.MASCULINO,
                12,
                3.70
        );

        assertEquals(
                TipoSaude.ZONA_SAUDAVEL,
                resultado
        );
    }

    @Test
    void deveClassificarCorrida20MetrosComoZonaDeRisco() {

        TipoSaude resultado = service.classificar(
                TipoTesteFisico.CORRIDA_20_METROS,
                TipoSexoBiologico.MASCULINO,
                12,
                4.10
        );

        assertEquals(
                TipoSaude.ZONA_DE_RISCO,
                resultado
        );
    }

    @Test
    void deveClassificarResultadoExatamenteNoPontoDeCorteComoZonaSaudavel() {

        TipoSaude resultado = service.classificar(
                TipoTesteFisico.CORRIDA_20_METROS,
                TipoSexoBiologico.MASCULINO,
                12,
                3.88
        );

        assertEquals(
                TipoSaude.ZONA_SAUDAVEL,
                resultado
        );
    }

    @Test
    void deveClassificarCorrida6MinutosComoZonaSaudavel() {

        TipoSaude resultado = service.classificar(
                TipoTesteFisico.CORRIDA_6_MINUTOS,
                TipoSexoBiologico.MASCULINO,
                12,
                1000
        );

        assertEquals(
                TipoSaude.ZONA_SAUDAVEL,
                resultado
        );
    }

    @Test
    void deveClassificarCorrida6MinutosComoZonaDeRisco() {

        TipoSaude resultado = service.classificar(
                TipoTesteFisico.CORRIDA_6_MINUTOS,
                TipoSexoBiologico.MASCULINO,
                12,
                900
        );

        assertEquals(
                TipoSaude.ZONA_DE_RISCO,
                resultado
        );
    }

    @Test
    void deveClassificarCorrida6MinutosNoPontoDeCorteComoZonaSaudavel() {

        TipoSaude resultado = service.classificar(
                TipoTesteFisico.CORRIDA_6_MINUTOS,
                TipoSexoBiologico.MASCULINO,
                12,
                966
        );

        assertEquals(
                TipoSaude.ZONA_SAUDAVEL,
                resultado
        );
    }

    @Test
    void deveClassificarFlexibilidadeComoZonaSaudavel() {

        TipoSaude resultado = service.classificar(
                TipoTesteFisico.FLEXIBILIDADE,
                TipoSexoBiologico.MASCULINO,
                12,
                35.0
        );

        assertEquals(
                TipoSaude.ZONA_SAUDAVEL,
                resultado
        );
    }

    @Test
    void deveClassificarFlexibilidadeComoZonaDeRisco() {

        TipoSaude resultado = service.classificar(
                TipoTesteFisico.FLEXIBILIDADE,
                TipoSexoBiologico.MASCULINO,
                12,
                25.0
        );

        assertEquals(
                TipoSaude.ZONA_DE_RISCO,
                resultado
        );
    }

    @Test
    void deveClassificarFlexibilidadeNoPontoDeCorteComoZonaSaudavel() {

        TipoSaude resultado = service.classificar(
                TipoTesteFisico.FLEXIBILIDADE,
                TipoSexoBiologico.MASCULINO,
                12,
                29.5
        );

        assertEquals(
                TipoSaude.ZONA_SAUDAVEL,
                resultado
        );
    }

    @Test
    void deveClassificarAbdominaisComoZonaSaudavel() {

        TipoSaude resultado = service.classificar(
                TipoTesteFisico.ABDOMINAIS_1_MINUTO,
                TipoSexoBiologico.MASCULINO,
                12,
                45
        );

        assertEquals(
                TipoSaude.ZONA_SAUDAVEL,
                resultado
        );
    }

    @Test
    void deveClassificarAbdominaisComoZonaDeRisco() {

        TipoSaude resultado = service.classificar(
                TipoTesteFisico.ABDOMINAIS_1_MINUTO,
                TipoSexoBiologico.MASCULINO,
                12,
                35
        );

        assertEquals(
                TipoSaude.ZONA_DE_RISCO,
                resultado
        );
    }

    @Test
    void deveClassificarAbdominaisNoPontoDeCorteComoZonaSaudavel() {

        TipoSaude resultado = service.classificar(
                TipoTesteFisico.ABDOMINAIS_1_MINUTO,
                TipoSexoBiologico.MASCULINO,
                12,
                41
        );

        assertEquals(
                TipoSaude.ZONA_SAUDAVEL,
                resultado
        );
    }

    @Test
    void deveClassificarMedicineBallComoZonaSaudavel() {

        TipoSaude resultado = service.classificar(
                TipoTesteFisico.MEDICINE_BALL_2KG,
                TipoSexoBiologico.MASCULINO,
                12,
                320.0
        );

        assertEquals(
                TipoSaude.ZONA_SAUDAVEL,
                resultado
        );
    }

    @Test
    void deveClassificarMedicineBallComoZonaDeRisco() {

        TipoSaude resultado = service.classificar(
                TipoTesteFisico.MEDICINE_BALL_2KG,
                TipoSexoBiologico.MASCULINO,
                12,
                250.0
        );

        assertEquals(
                TipoSaude.ZONA_DE_RISCO,
                resultado
        );
    }

    @Test
    void deveClassificarMedicineBallNoPontoDeCorteComoZonaSaudavel() {

        TipoSaude resultado = service.classificar(
                TipoTesteFisico.MEDICINE_BALL_2KG,
                TipoSexoBiologico.MASCULINO,
                12,
                290.0
        );

        assertEquals(
                TipoSaude.ZONA_SAUDAVEL,
                resultado
        );
    }

    @Test
    void deveLancarRegraNegocioExceptionQuandoNaoExisteReferenciaDeSaude() {

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
    void deveLancarExcecaoQuandoParametrosDeSaudeForemInvalidos() {

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
}