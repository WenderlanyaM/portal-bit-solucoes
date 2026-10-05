package info.bitsolucoes.portal.controller;

import info.bitsolucoes.portal.model.Ticket;
import info.bitsolucoes.portal.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketApiController {

    private final TicketService ticketService;

    public TicketApiController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> listarApi() {
        List<Ticket> tickets = ticketService.listarTodas();
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> buscarPorIdApi(@PathVariable Long id) {
        Ticket ticket = ticketService.buscarPorId(id);
        return ResponseEntity.ok(ticket);
    }
}