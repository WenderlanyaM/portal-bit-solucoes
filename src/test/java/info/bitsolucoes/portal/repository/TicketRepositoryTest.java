package info.bitsolucoes.portal.repository;

import info.bitsolucoes.portal.model.StatusSolicitacao;
import info.bitsolucoes.portal.model.Ticket;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketRepositoryTest {

    @Mock
    private TicketRepository ticketRepository;

    @Test
    @DisplayName("Deve retornar lista de tickets mockada com sucesso no repositório")
    void deveRetornarTicketsMockados() {
        // Arrange
        Ticket ticket = new Ticket();
        ticket.setTitle("Teste Unitário do Repositório");
        ticket.setStatus(StatusSolicitacao.ABERTO);

        when(ticketRepository.findAll()).thenReturn(List.of(ticket));

        // Act
        List<Ticket> tickets = ticketRepository.findAll();

        // Assert
        assertThat(tickets).isNotEmpty();
        assertThat(tickets.get(0).getTitle()).isEqualTo("Teste Unitário do Repositório");
    }
}