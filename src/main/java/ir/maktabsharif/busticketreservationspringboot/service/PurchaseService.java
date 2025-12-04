package ir.maktabsharif.busticketreservationspringboot.service;

import ir.maktabsharif.busticketreservationspringboot.dto.PurchaseDto;
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

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public Optional<Purchase> findById(Long purchaseId) {
        return purchaseRepository.findById(purchaseId);
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


    public Purchase getPurchaseById(Long purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new RuntimeException("Purchase not found"));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        if (!purchase.getUser().getUsername().equals(username)) {
            throw new RuntimeException("شما مجاز به مشاهده این بلیط نیستید");
        }

        return purchase;
    }

    public void cancelPurchase(Long purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new RuntimeException("Purchase not found"));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        if (!purchase.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Not authorized to cancel this purchase");
        }

        purchaseRepository.delete(purchase);
    }

}