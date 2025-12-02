package ir.maktabsharif.busticketreservationspringboot.repository;

import ir.maktabsharif.busticketreservationspringboot.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
    @Query("SELECT t FROM Ticket t WHERE t.departureCity = :departureCity AND t.destinationCity = :destinationCity AND t.departureDate = :departureDate ORDER BY t.departureTime ASC")
    List<Ticket> findAvailableTickets(@Param("departureCity") String departureCity,
                                    @Param("destinationCity") String destinationCity,
                                    @Param("departureDate") LocalDate departureDate);

    List<Ticket> findByDepartureCityAndDestinationCityAndDepartureDate(String departureCity, String destinationCity, LocalDate departureDate);



    @Query("SELECT t FROM Ticket t WHERE t.departureDate >= CURRENT_DATE ORDER BY t.departureDate ASC, t.departureTime ASC")
    List<Ticket> findUpcomingTickets();
}
