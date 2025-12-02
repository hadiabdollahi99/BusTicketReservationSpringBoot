package ir.maktabsharif.busticketreservationspringboot.service;

import ir.maktabsharif.busticketreservationspringboot.dto.PurchaseDto;
import ir.maktabsharif.busticketreservationspringboot.dto.SearchDto;
import ir.maktabsharif.busticketreservationspringboot.model.Purchase;
import ir.maktabsharif.busticketreservationspringboot.model.Ticket;
import ir.maktabsharif.busticketreservationspringboot.model.User;
import ir.maktabsharif.busticketreservationspringboot.repository.PurchaseRepository;
import ir.maktabsharif.busticketreservationspringboot.repository.TicketRepository;
import ir.maktabsharif.busticketreservationspringboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;


@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final PurchaseRepository purchaseRepository;

    public List<Ticket> searchTickets(SearchDto searchDto) {
        return ticketRepository.findAvailableTickets(
                searchDto.getDepartureCity(),
                searchDto.getDestinationCity(),
                searchDto.getDepartureDate()
        );
    }

    public Purchase purchaseTicket(PurchaseDto purchaseDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Ticket ticket = ticketRepository.findById(purchaseDto.getTicketId())
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        int randomNum = ThreadLocalRandom.current().nextInt(1000, 10000);
        String randomString = String.valueOf(randomNum);

        Purchase purchase = Purchase.builder()
                .customerName(purchaseDto.getCustomerName())
                .gender(purchaseDto.getGender())
                .ticketNumber(randomString)
                .user(user)
                .ticket(ticket)
                .build();

        return purchaseRepository.save(purchase);
    }

    public List<Purchase> getUserPurchases() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        return purchaseRepository.findByUsername(username);
    }

    public void cancelPurchase(Long purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new RuntimeException("Purchase not found"));

        // Verify ownership
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        if (!purchase.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Not authorized to cancel this purchase");
        }

        purchaseRepository.delete(purchase);
    }

    public void initializeSampleData() {
        // Create sample tickets
        if (ticketRepository.count() == 0) {
            Ticket ticket1 = Ticket.builder()
                    .departureCity("Tehran")
                    .destinationCity("Mashhad")
                    .departureDate(LocalDate.now().plusDays(1))
                    .departureTime(LocalTime.of(8, 0))
                    .tripNumber("THR-MHD-001")
                    .build();

            Ticket ticket2 = Ticket.builder()
                    .departureCity("Tehran")
                    .destinationCity("Mashhad")
                    .departureDate(LocalDate.now().plusDays(1))
                    .departureTime(LocalTime.of(14, 30))
                    .tripNumber("THR-MHD-002")
                    .build();

            Ticket ticket3 = Ticket.builder()
                    .departureCity("Tehran")
                    .destinationCity("Isfahan")
                    .departureDate(LocalDate.now().plusDays(2))
                    .departureTime(LocalTime.of(10, 0))
                    .tripNumber("THR-ESF-001")
                    .build();

            ticketRepository.saveAll(List.of(ticket1, ticket2, ticket3));
        }
    }
}