package info.bitsolucoes.portal.repository;

import info.bitsolucoes.portal.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT t.status, COUNT(t) FROM Ticket t GROUP BY t.status")
    List<Object[]> countGroupedByStatus();
}