package ir.maktabsharif.busticketreservationspringboot.service;

import ir.maktabsharif.busticketreservationspringboot.dto.TicketSearchDto;
import ir.maktabsharif.busticketreservationspringboot.model.Ticket;
import ir.maktabsharif.busticketreservationspringboot.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    public List<Ticket> searchTickets(TicketSearchDto searchDto) {
        return ticketRepository.findAvailableTickets(
                searchDto.getDepartureCity(),
                searchDto.getDestinationCity(),
                searchDto.getDepartureDate()
        );
    }

    public List<Ticket> getUpcomingTickets() {
        return ticketRepository.findUpcomingTickets();
    }

    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("بلیط یافت نشد"));
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }
}