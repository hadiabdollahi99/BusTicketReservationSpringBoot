package ir.maktabsharif.busticketreservationspringboot.repository;

import ir.maktabsharif.busticketreservationspringboot.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT t FROM Ticket t WHERE t.departureCity = :departure " +
            "AND t.destinationCity = :destination " +
            "AND t.departureDate = :date " +
            "ORDER BY t.departureTime ASC")
    List<Ticket> findAvailableTickets(
            @Param("departure") String departure,
            @Param("destination") String destination,
            @Param("date") LocalDate date);
}