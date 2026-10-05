package info.bitsolucoes.portal.controller;

import info.bitsolucoes.portal.service.TicketService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketControllerTest {

    @Mock
    private TicketService ticketService;

    @Mock
    private Model model;

    @InjectMocks
    private TicketController ticketController;

    @Test
    @DisplayName("Deve listar os tickets com sucesso e retornar a view correta")
    void deveRetornarViewDeListagem() {
        // Arrange
        when(ticketService.filtrarEOrdenar(any(), any(), any(), any(), any(), any()))
                .thenReturn(Collections.emptyList());

        // Act
        String viewName = ticketController.listar(null, null, null, null, null, "data", model);

        // Assert
        assertEquals("tickets", viewName);
        verify(ticketService).filtrarEOrdenar(any(), any(), any(), any(), any(), any());
    }
}