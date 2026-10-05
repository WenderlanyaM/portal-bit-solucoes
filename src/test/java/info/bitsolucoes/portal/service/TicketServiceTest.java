package info.bitsolucoes.portal.service;

import info.bitsolucoes.portal.model.StatusSolicitacao;
import info.bitsolucoes.portal.model.Ticket;
import info.bitsolucoes.portal.repository.CategoryRepository;
import info.bitsolucoes.portal.repository.TicketRepository;
import info.bitsolucoes.portal.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private TicketService ticketService;

    @Test
    @DisplayName("Deve lançar exceção ao tentar excluir solicitação que não está com status ABERTO")
    void deveLancarExcecaoAoExcluirSolicitacaoNaoAberta() {
        // Arrange
        Long ticketId = 1L;
        Ticket ticketMock = new Ticket();
        ticketMock.setId(ticketId);
        ticketMock.setStatus(StatusSolicitacao.EM_ATENDIMENTO); // Status diferente de ABERTO

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticketMock));

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            ticketService.excluir(ticketId);
        });

        assertEquals("Apenas solicitações com status ABERTO podem ser excluídas.", exception.getMessage());
        verify(ticketRepository, never()).delete(any(Ticket.class));
    }

    @Test
    @DisplayName("Deve excluir solicitação quando status for ABERTO")
    void deveExcluirSolicitacaoQuandoStatusAberto() {
        // Arrange
        Long ticketId = 1L;
        Ticket ticketMock = new Ticket();
        ticketMock.setId(ticketId);
        ticketMock.setStatus(StatusSolicitacao.ABERTO);

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticketMock));

        // Act
        ticketService.excluir(ticketId);

        // Assert
        verify(ticketRepository, times(1)).delete(ticketMock);
    }
}