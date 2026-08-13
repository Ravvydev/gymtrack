package com.ravvy.gymtrack.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders
                .standaloneSetup(new TestController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void deveRetornar404QuandoEntidadeNaoForEncontrada() throws Exception {

        mockMvc.perform(
                        get("/teste/not-found")
                )
                .andExpect(status().isNotFound())
                .andExpect(
                        jsonPath("$.status").value(404)
                )
                .andExpect(
                        jsonPath("$.erro")
                                .value("Recurso não encontrado")
                )
                .andExpect(
                        jsonPath("$.mensagem")
                                .value("Recurso não encontrado para teste")
                )
                .andExpect(
                        jsonPath("$.path")
                                .value("/teste/not-found")
                );
    }

    @Test
    void deveRetornar400QuandoRegraDeNegocioForViolada() throws Exception {

        mockMvc.perform(
                        get("/teste/regra-negocio")
                )
                .andExpect(status().isBadRequest())
                .andExpect(
                        jsonPath("$.status").value(400)
                )
                .andExpect(
                        jsonPath("$.erro")
                                .value("Regra de negócio")
                )
                .andExpect(
                        jsonPath("$.mensagem")
                                .value("Regra de negócio inválida")
                )
                .andExpect(
                        jsonPath("$.path")
                                .value("/teste/regra-negocio")
                );
    }

    @Test
    void deveRetornar400QuandoValidacaoFalhar() throws Exception {

        String json = """
                {
                    "nome": ""
                }
                """;

        mockMvc.perform(
                        post("/teste/validacao")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(
                        jsonPath("$.status").value(400)
                )
                .andExpect(
                        jsonPath("$.erro")
                                .value("Erro de validação")
                )
                .andExpect(
                        jsonPath("$.mensagem")
                                .value(org.hamcrest.Matchers.containsString("nome:"))
                )
                .andExpect(
                        jsonPath("$.path")
                                .value("/teste/validacao")
                );
    }

    @RestController
    @RequestMapping("/teste")
    static class TestController {

        @GetMapping("/not-found")
        public void notFound() {

            throw new EntityNotFoundException(
                    "Recurso não encontrado para teste"
            );
        }

        @GetMapping("/regra-negocio")
        public void regraNegocio() {

            throw new RegraDeNegocioExeption(
                    "Regra de negócio inválida"
            );
        }

        @PostMapping("/validacao")
        public void validacao(
                @RequestBody @Valid TestRequest request
        ) {
        }
    }

    record TestRequest(

            @NotBlank
            String nome

    ) {
    }
}