package br.com.alura.adopet.api.validacoes;

import static org.mockito.BDDMockito.willReturn;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.TutorRepository;

@ExtendWith(MockitoExtension.class)
public class ValidacaoTutorComLimiteDeAdocoesTest {
    @InjectMocks
    private ValidacaoTutorComLimiteDeAdocoes validacao;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private TutorRepository tutorRepository;
    @Mock
    private SolicitacaoAdocaoDto dto;

    @Test
    void naoDeveriaAceitarSolicitacaodeNovaAdocao() {

        // Arrange
        BDDMockito.given(adocaoRepository.countByTutorIdAndStatus(dto.idTutor(), StatusAdocao.AGUARDANDO_AVALIACAO))
                .willReturn(5);

        // ACT        // ASSERT
        Assertions.assertThrows(ValidacaoException.class, ()-> validacao.validar(dto));
    }

    @Test
    void DeveriaAceitarSolicitacaodeNovaAdocao() {

        // Arrange
        BDDMockito.given(adocaoRepository.countByTutorIdAndStatus(dto.idTutor(), StatusAdocao.AGUARDANDO_AVALIACAO))
                .willReturn(4);

        // ACT        // ASSERT
        Assertions.assertDoesNotThrow(()-> validacao.validar(dto));
    }


}
