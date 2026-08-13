package com.ravvy.gymtrack.service;

import com.ravvy.gymtrack.exception.RegraDeNegocioExeption;
import com.ravvy.gymtrack.util.TipoClassificacao;
import com.ravvy.gymtrack.util.TipoSexoBiologico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ClassificacaoImcServiceTest {

    private ClassificacaoImcService service;

    @BeforeEach
    void setUp() {
        service = new ClassificacaoImcService();
    }

    @Test
    void deveClassificarImcAbaixoDoPontoDeCorteComoZonaSaudavel() {

        TipoClassificacao resultado = service.classificar(
                TipoSexoBiologico.MASCULINO,
                12,
                20.0
        );

        assertEquals(
                TipoClassificacao.ZONA_SAUDAVEL,
                resultado
        );
    }

    @Test
    void deveClassificarImcIgualAoPontoDeCorteComoZonaSaudavel() {

        TipoClassificacao resultado = service.classificar(
                TipoSexoBiologico.MASCULINO,
                12,
                22.2
        );

        assertEquals(
                TipoClassificacao.ZONA_SAUDAVEL,
                resultado
        );
    }

    @Test
    void deveClassificarImcAcimaDoPontoDeCorteComoZonaDeRisco() {

        TipoClassificacao resultado = service.classificar(
                TipoSexoBiologico.MASCULINO,
                12,
                22.3
        );

        assertEquals(
                TipoClassificacao.ZONA_DE_RISCO,
                resultado
        );
    }

    @Test
    void deveUsarPontoDeCorteCorretoParaFemininoDe17Anos() {

        TipoClassificacao resultado = service.classificar(
                TipoSexoBiologico.FEMININO,
                17,
                24.1
        );

        assertEquals(
                TipoClassificacao.ZONA_DE_RISCO,
                resultado
        );
    }

    @Test
    void deveClassificarFeminino17AnosNoPontoDeCorteComoSaudavel() {

        TipoClassificacao resultado = service.classificar(
                TipoSexoBiologico.FEMININO,
                17,
                24.0
        );

        assertEquals(
                TipoClassificacao.ZONA_SAUDAVEL,
                resultado
        );
    }

    @Test
    void deveLancarExcecaoQuandoSexoForNulo() {

        assertThrows(
                RegraDeNegocioExeption.class,
                () -> service.classificar(
                        null,
                        12,
                        20.0
                )
        );
    }

    @Test
    void deveLancarExcecaoQuandoIdadeForInvalida() {

        assertThrows(
                RegraDeNegocioExeption.class,
                () -> service.classificar(
                        TipoSexoBiologico.MASCULINO,
                        18,
                        20.0
                )
        );
    }

    @Test
    void deveLancarExcecaoQuandoImcForInvalido() {

        assertThrows(
                RegraDeNegocioExeption.class,
                () -> service.classificar(
                        TipoSexoBiologico.MASCULINO,
                        12,
                        0.0
                )
        );
    }
}