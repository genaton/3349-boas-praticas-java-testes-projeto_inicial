package br.com.alura.adopet.api.service;

import static org.mockito.BDDMockito.then;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.dto.PetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;

@ExtendWith(MockitoExtension.class)
public class PetServiceTest {
    @InjectMocks
    private PetService service;
    @Mock
    private PetRepository petRepository;
    @Mock
    private PetDto dto;
    @Mock
    private Abrigo abrigo;
    @Mock
    private CadastroPetDto cadastroPetDto;

        @Test
    void deveriaCadastrarPet(){

        service.cadastrarPet(abrigo, cadastroPetDto);
        then(petRepository).should().save(new Pet(cadastroPetDto, abrigo));
        
    }

    @Test
    void deveriaListarTodosPetsDisponiveis(){

        service.buscarPetsDisponiveis();

        then(petRepository).should().findAllByAdotadoFalse();

    }


    

}
