package ir.maktabsharif.busticketreservationspringboot.service;


import ir.maktabsharif.busticketreservationspringboot.dto.TicketPurchaseDto;
import ir.maktabsharif.busticketreservationspringboot.model.Purchase;
import ir.maktabsharif.busticketreservationspringboot.model.Ticket;
import ir.maktabsharif.busticketreservationspringboot.model.User;
import ir.maktabsharif.busticketreservationspringboot.repository.PurchaseRepository;
import ir.maktabsharif.busticketreservationspringboot.repository.TicketRepository;
import ir.maktabsharif.busticketreservationspringboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final PurchaseRepository purchaseRepository;

    private final UserService userService;


    public Purchase purchaseTicket(TicketPurchaseDto ticketDto, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("کاربر یافت نشد"));

        Ticket ticket = ticketRepository.findById(ticketDto.getTicketId())
                .orElseThrow(() -> new RuntimeException("سفر یافت نشد"));

        // Check if user already has a purchase for this ticket
        List<Purchase> existingPurchases = purchaseRepository.findPurchaseByUserAndTicketId(user, ticket.getId());
        if (!existingPurchases.isEmpty()) {
            throw new RuntimeException("شما قبلاً این بلیط را خریداری کرده‌اید");
        }

        Purchase purchase = Purchase.builder()
                .customerName(ticketDto.getCustomerName())
                .customerGender(ticketDto.getGender())
                .ticket(ticket)
                .user(user)
                .ticketNumber(generateUniqueTicketNumber()).build();


        return purchaseRepository.save(purchase);
    }

    public List<Purchase> getUserPurchases(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("کاربر یافت نشد"));

        return purchaseRepository.findByUserOrderByCreatedAtDesc(user);
    }

    public Purchase getPurchaseById(Long purchaseId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("کاربر یافت نشد"));

        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new RuntimeException("خرید یافت نشد"));

        if (!purchase.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("شما اجازه دسترسی به این بلیط را ندارید");
        }

        return purchase;
    }

    public void cancelPurchase(Long purchaseId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("کاربر یافت نشد"));

        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new RuntimeException("خرید یافت نشد"));

        if (!purchase.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("شما اجازه لغو این بلیط را ندارید");
        }


        // Check if cancellation is allowed (e.g., at least 2 hours before departure)
        LocalDateTime departureDateTime = purchase.getTicket().getDepartureDate()
                .atTime(purchase.getTicket().getDepartureTime());

        if (LocalDateTime.now().plusHours(2).isAfter(departureDateTime)) {
            throw new RuntimeException("امکان لغو بلیط کمتر از ۲ ساعت قبل از حرکت وجود ندارد");
        }


        purchaseRepository.delete(purchase);
    }

    private String generateUniqueTicketNumber() {
        String ticketNumber;
        do {
            ticketNumber = "TKT" + RandomStringUtils.randomNumeric(4);
        } while (purchaseRepository.existsByTicketNumber(ticketNumber));

        return ticketNumber;
    }
}