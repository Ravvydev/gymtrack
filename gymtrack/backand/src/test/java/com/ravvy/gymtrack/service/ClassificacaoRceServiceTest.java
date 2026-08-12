package com.ravvy.gymtrack.service;

import com.ravvy.gymtrack.util.TipoClassificacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClassificacaoRceServiceTest {

    private ClassificacaoRceService service;

    @BeforeEach
    void setUp() {
        service = new ClassificacaoRceService();
    }

    @Test
    void deveClassificarRceAbaixoDoPontoDeCorteComoZonaSaudavel() {

        TipoClassificacao resultado =
                service.classificar(0.49);

        assertEquals(
                TipoClassificacao.ZONA_SAUDAVEL,
                resultado
        );
    }

    @Test
    void deveClassificarRceIgualAoPontoDeCorteComoZonaSaudavel() {

        TipoClassificacao resultado =
                service.classificar(0.50);

        assertEquals(
                TipoClassificacao.ZONA_SAUDAVEL,
                resultado
        );
    }

    @Test
    void deveClassificarRceAcimaDoPontoDeCorteComoZonaDeRisco() {

        TipoClassificacao resultado =
                service.classificar(0.51);

        assertEquals(
                TipoClassificacao.ZONA_DE_RISCO,
                resultado
        );
    }
}