package br.com.alura.adopet.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.service.AbrigoService;
import br.com.alura.adopet.api.service.PetService;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class AbrigoControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private AbrigoService service;

    @MockBean
    private PetService petService;

    @Mock
    private Abrigo abrigo;

    @Autowired
    private JacksonTester<CadastroAbrigoDto> jsonDto;

    @Test
    void deveriaDevolverCodigo200ParaRequisicaoDeListarAbrigos() throws Exception {
        // ACT
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.get("/abrigos"))
                .andReturn().getResponse();

        // ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaDevolverCod200ParaCadastrarAbrigo() throws Exception {

        CadastroAbrigoDto dto = new CadastroAbrigoDto("Petinho", "(99)99999-9999", "petinho@email.com");

        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.post("/abrigos")
                        .content(jsonDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    void deveriaDevolverCod400ParaCadastrarAbrigo() throws Exception {

        CadastroAbrigoDto dto = new CadastroAbrigoDto("Petinho", "(99)99999-99990", "petinho@email.com");

        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.post("/abrigos")
                        .content(jsonDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());

    }

    @Test
    void deveriaDevolverCod200ParaListarPetsdoAbrigoPorNome() throws Exception {

        String nome = "Petinho";

        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.get("/abrigos/{nome}/pets", nome))
                .andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    void deveriaDevolverCod200ParaListarPetsdoAbrigoPorId() throws Exception {

        Long id = 1l;

        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.get("/abrigos/{id}/pets", id))
                .andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());

    }

    
    @Test
    void deveriaDevolverCod404ParaListarPetsdoAbrigoPorIdInvalido() throws Exception {

        Long id = 1l;
        given(service.listarPetsDoAbrigo(id.toString()))
        .willThrow(ValidacaoException.class);

        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.get("/abrigos/{id}/pets", id.toString()))
                .andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());

    }
      @Test
    void deveriaDevolverCod404ParaListarPetsdoAbrigoPorNomeInvalido() throws Exception {

        String nome = "Petinho";
        given(service.listarPetsDoAbrigo(nome))
        .willThrow(ValidacaoException.class);

        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.get("/abrigos/{nome}/pets", nome))
                .andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());

    }

}
