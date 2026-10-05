package info.bitsolucoes.portal.service;

import info.bitsolucoes.portal.dto.TicketFormDto;
import info.bitsolucoes.portal.exception.SolicitacaoNaoEncontradaException;
import info.bitsolucoes.portal.model.Category;
import info.bitsolucoes.portal.model.StatusSolicitacao;
import info.bitsolucoes.portal.model.Ticket;
import info.bitsolucoes.portal.model.User;
import info.bitsolucoes.portal.repository.CategoryRepository;
import info.bitsolucoes.portal.repository.TicketRepository;
import info.bitsolucoes.portal.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public TicketService(TicketRepository ticketRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Ticket> listarTodas() {
        return ticketRepository.findAll();
    }

    public Ticket buscarPorId(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new SolicitacaoNaoEncontradaException("Solicitação não encontrada com ID: " + id));
    }

    @Transactional
    public Ticket criar(TicketFormDto dto, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Category category = categoryRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada"));

        Ticket ticket = new Ticket();
        ticket.setTitle(dto.titulo());
        ticket.setDescription(dto.descricao());
        ticket.setCategory(category);
        ticket.setUser(user);
        ticket.setStatus(StatusSolicitacao.ABERTO);

        return ticketRepository.save(ticket);
    }

    @Transactional
    public void excluir(Long id) {
        Ticket ticket = buscarPorId(id);
        if (ticket.getStatus() != StatusSolicitacao.ABERTO) {
            throw new IllegalStateException("Apenas solicitações com status ABERTO podem ser excluídas.");
        }
        ticketRepository.delete(ticket);
    }

    public Map<StatusSolicitacao, Long> obterEstatisticasDashboard() {
        List<Ticket> tickets = ticketRepository.findAll();
        Map<StatusSolicitacao, Long> estatisticas = new HashMap<>();

        estatisticas.put(StatusSolicitacao.ABERTO, tickets.stream().filter(t -> t.getStatus() == StatusSolicitacao.ABERTO).count());
        estatisticas.put(StatusSolicitacao.EM_ATENDIMENTO, tickets.stream().filter(t -> t.getStatus() == StatusSolicitacao.EM_ATENDIMENTO).count());
        estatisticas.put(StatusSolicitacao.CONCLUIDO, tickets.stream().filter(t -> t.getStatus() == StatusSolicitacao.CONCLUIDO).count());

        return estatisticas;
    }

    public List<Ticket> filtrarEOrdenar(String titulo, Long categoriaId, StatusSolicitacao status, LocalDate dataInicio, LocalDate dataFim, String orderBy) {
        List<Ticket> tickets = ticketRepository.findAll();

        // Aplica os filtros
        tickets = tickets.stream()
                .filter(t -> titulo == null || titulo.isEmpty() || t.getTitle().toLowerCase().contains(titulo.toLowerCase()))
                .filter(t -> categoriaId == null || t.getCategory().getId().equals(categoriaId))
                .filter(t -> status == null || t.getStatus() == status)
                .filter(t -> dataInicio == null || !t.getCreatedAt().toLocalDate().isBefore(dataInicio))
                .filter(t -> dataFim == null || !t.getCreatedAt().toLocalDate().isAfter(dataFim))
                .collect(Collectors.toList());

        // Aplica a ordenação
        if (orderBy != null) {
            switch (orderBy) {
                case "id": tickets.sort(Comparator.comparing(Ticket::getId)); break;
                case "titulo": tickets.sort(Comparator.comparing(Ticket::getTitle)); break;
                case "categoria": tickets.sort(Comparator.comparing(t -> t.getCategory().getName())); break;
                case "solicitante": tickets.sort(Comparator.comparing(t -> t.getUser().getUsername())); break;
                case "data": tickets.sort(Comparator.comparing(Ticket::getCreatedAt).reversed()); break;
                case "status": tickets.sort(Comparator.comparing(Ticket::getStatus)); break;
            }
        }
        return tickets;
    }

    @Transactional
    public void atualizarStatus(Long id, StatusSolicitacao status) {
        Ticket ticket = buscarPorId(id);
        ticket.setStatus(status);
        ticketRepository.save(ticket);
    }

    @Transactional
    public void atualizarDados(Long id, String titulo, String descricao) {
        Ticket ticket = buscarPorId(id);
        ticket.setTitle(titulo);
        ticket.setDescription(descricao);
        ticketRepository.save(ticket);
    }
}