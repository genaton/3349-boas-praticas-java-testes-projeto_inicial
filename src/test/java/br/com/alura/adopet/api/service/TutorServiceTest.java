package br.com.alura.adopet.api.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.alura.adopet.api.dto.AtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;

@ExtendWith(MockitoExtension.class)
public class TutorServiceTest {
    @InjectMocks
    private TutorService service;
    @Mock
    private TutorRepository tutorRepository;
    @Mock
    private CadastroTutorDto tutorDto;
    @Mock
    private AtualizacaoTutorDto atualizacaoTutorDto;
    @Mock
    private Tutor tutor;

    @Test
    void deveriaAtualizarTutor(){
        given(tutorRepository.getReferenceById(atualizacaoTutorDto.id())).willReturn(tutor);

        service.atualizar(atualizacaoTutorDto);

        then(tutor).should().atualizarDados(atualizacaoTutorDto);


    }

    @Test
    void deveriaCadastrarTutor(){
        given(tutorRepository.existsByTelefoneOrEmail(tutorDto.telefone(), tutorDto.email())).willReturn(false);

        assertDoesNotThrow(()-> service.cadastrar(tutorDto));
        then(tutorRepository).should().save(new Tutor(tutorDto));


        
    }

    @Test
    void NaoDeveriaCadastrarTutor(){
        given(tutorRepository.existsByTelefoneOrEmail(tutorDto.telefone(), tutorDto.email())).willReturn(true);

        assertThrows(ValidacaoException.class, ()-> service.cadastrar(tutorDto));
        


        
    }
    
    


}
