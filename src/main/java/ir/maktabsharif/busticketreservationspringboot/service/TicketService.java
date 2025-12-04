package ir.maktabsharif.busticketreservationspringboot.service;

import ir.maktabsharif.busticketreservationspringboot.dto.SearchDto;
import ir.maktabsharif.busticketreservationspringboot.model.Ticket;
import ir.maktabsharif.busticketreservationspringboot.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {


    private final TicketRepository ticketRepository;


    public List<Ticket> searchTickets(SearchDto searchDto) {
        return ticketRepository.findAvailableTickets(
                searchDto.getDepartureCity(),
                searchDto.getDestinationCity(),
                searchDto.getDepartureDate()
        );
    }
}