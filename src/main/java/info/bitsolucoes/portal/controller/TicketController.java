package info.bitsolucoes.portal.controller;

import info.bitsolucoes.portal.dto.TicketFormDto;
import info.bitsolucoes.portal.model.StatusSolicitacao;
import info.bitsolucoes.portal.model.Ticket;
import info.bitsolucoes.portal.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("estatisticas", ticketService.obterEstatisticasDashboard());
        model.addAttribute("tickets", ticketService.listarTodas());
        return "dashboard";
    }

    @GetMapping("/tickets")
    public String listar(@RequestParam(required = false) String titulo,
                         @RequestParam(required = false, name = "categoria") Long categoriaId,
                         @RequestParam(required = false) StatusSolicitacao status,
                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataInicio,
                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataFim,
                         @RequestParam(defaultValue = "data") String orderBy,
                         Model model) {

        List<Ticket> tickets = ticketService.filtrarEOrdenar(titulo, categoriaId, status, dataInicio, dataFim, orderBy);
        model.addAttribute("tickets", tickets);
        return "tickets";
    }

    @GetMapping("/tickets/novo")
    public String novoForm(Model model) {
        model.addAttribute("ticketForm", new TicketFormDto("", "", null));
        return "add_ticket";
    }

    @PostMapping("/tickets")
    public String criar(@Valid @ModelAttribute("ticketForm") TicketFormDto dto,
                        BindingResult result,
                        Authentication authentication) {
        if (result.hasErrors()) {
            return "add_ticket";
        }
        ticketService.criar(dto, authentication.getName());
        return "redirect:/tickets";
    }

    @GetMapping("/tickets/{id}")
    public String visualizar(@PathVariable Long id, Model model) {
        model.addAttribute("ticket", ticketService.buscarPorId(id));
        return "view_ticket";
    }

    @GetMapping("/tickets/{id}/edit")
    public String editarForm(@PathVariable Long id, Model model) {
        model.addAttribute("ticket", ticketService.buscarPorId(id));
        return "edit_ticket";
    }

    @PostMapping("/tickets/{id}/edit")
    public String atualizar(@PathVariable Long id, @RequestParam String title, @RequestParam String description) {
        ticketService.atualizarDados(id, title, description);
        return "redirect:/tickets";
    }

    @PostMapping("/tickets/{id}/status")
    public String alterarStatus(@PathVariable Long id, @RequestParam StatusSolicitacao status) {
        ticketService.atualizarStatus(id, status);
        return "redirect:/tickets";
    }

    @PostMapping("/tickets/{id}/delete")
    public String excluir(@PathVariable Long id) {
        ticketService.excluir(id);
        return "redirect:/tickets";
    }
}