package br.com.alura.adopet.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
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

import br.com.alura.adopet.api.dto.AtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.repository.TutorRepository;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class TutorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TutorRepository repository;

    @Autowired
    private JacksonTester<CadastroTutorDto> jsonDto;
    @Autowired
    private JacksonTester<AtualizacaoTutorDto> jsonAtuaDto;

    @Test
    void deveriaDevolverCodigo200ParaCadastroDeTutor() throws Exception {

        // ACT
        CadastroTutorDto dto = new CadastroTutorDto("João", "(99)99999-9999", "joao@email.com");
       
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.post("/tutores")
                        .content(jsonDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();


        // ASSERT
        assertEquals(200, response.getStatus());
    }

     @Test
    void deveriaDevolverCodigo400ParaCadastroDeTutor() throws Exception {

        // ACT
        CadastroTutorDto dto = new CadastroTutorDto("João", "(99)99999-99990", "joao@email.com");
         MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.post("/tutores")
                        .content(jsonDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // ASSERT
        assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo200ParaAtualizacaoDeTutor() throws Exception {

        // ACT
        AtualizacaoTutorDto dto = new AtualizacaoTutorDto(1l,"João", "(99)99999-9999", "joao@email.com");
       
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.post("/tutores")
                        .content(jsonAtuaDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();


        // ASSERT
        assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo400ParaAtualizacaoDeTutorInvalido() throws Exception {

        // ACT
        AtualizacaoTutorDto dto = new AtualizacaoTutorDto(1l,"João", "(99)99999-99990", "joao@email.com");
       
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.post("/tutores")
                        .content(jsonAtuaDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();


        // ASSERT
        assertEquals(400, response.getStatus());
    }


}
