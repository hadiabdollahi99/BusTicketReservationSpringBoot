package ir.maktabsharif.busticketreservationspringboot.controller;

import ir.maktabsharif.busticketreservationspringboot.dto.TicketPurchaseDto;
import ir.maktabsharif.busticketreservationspringboot.model.Gender;
import ir.maktabsharif.busticketreservationspringboot.model.Purchase;
import ir.maktabsharif.busticketreservationspringboot.model.Ticket;
import ir.maktabsharif.busticketreservationspringboot.model.User;
import ir.maktabsharif.busticketreservationspringboot.service.PurchaseService;
import ir.maktabsharif.busticketreservationspringboot.service.TicketService;
import ir.maktabsharif.busticketreservationspringboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final UserService userService;
    private final TicketService ticketService;
    private final PurchaseService purchaseService;


    @GetMapping("/purchase/{ticketId}")
    public String showPurchaseForm(@PathVariable Long ticketId, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getName().equals("anonymousUser")) {
            return "redirect:/auth/login";
        }

        Ticket ticket = ticketService.getTicketById(ticketId);

        TicketPurchaseDto ticketDto = new TicketPurchaseDto();
        ticketDto.setTicketId(ticketId);

        User user = userService.findByUsername(auth.getName());
        ticketDto.setCustomerName(user.getUsername());

        model.addAttribute("ticketDto", ticketDto);
        model.addAttribute("ticket", ticket);
        model.addAttribute("genders", Gender.values());

        return "ticket/purchase";
    }

    @PostMapping("/purchase")
    public String purchaseTicket(@ModelAttribute("ticketDto") TicketPurchaseDto ticketDto,
                                 BindingResult result,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getName().equals("anonymousUser")) {
            return "redirect:/auth/login";
        }

        if (result.hasErrors()) {
            Ticket ticket = ticketService.getTicketById(ticketDto.getTicketId());
            model.addAttribute("ticket", ticket);
            model.addAttribute("genders", Gender.values());
            return "ticket/purchase";
        }

        try {
            Purchase purchase = purchaseService.purchaseTicket(ticketDto, auth.getName());
            redirectAttributes.addFlashAttribute("successMessage",
                    "بلیط با موفقیت خریداری شد. شماره بلیط: " + purchase.getTicketNumber());
            return "redirect:/tickets/my-tickets";
        } catch (RuntimeException e) {
            Ticket ticket = ticketService.getTicketById(ticketDto.getTicketId());
            model.addAttribute("ticket", ticket);
            model.addAttribute("genders", Gender.values());
            model.addAttribute("errorMessage", e.getMessage());
            return "ticket/purchase";
        }
    }

    @GetMapping("/my-purchases")
    public String showMyPurchases(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getName().equals("anonymousUser")) {
            return "redirect:/auth/login";
        }

        List<Purchase> purchases = purchaseService.getUserPurchases(auth.getName());
        model.addAttribute("purchases", purchases);

        return "ticket/my-purchases";
    }

    @GetMapping("/all")
    public String showAllPurchases(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getName().equals("anonymousUser")) {
            return "redirect:/auth/login";
        }

        List<Purchase> purchases = purchaseService.getUserPurchases(auth.getName());
        model.addAttribute("purchases", purchases);

        return "ticket/all-purchases";
    }

    @GetMapping("/view/{id}")
    public String viewPurchase(@PathVariable Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getName().equals("anonymousUser")) {
            return "redirect:/auth/login";
        }

        try {
            Purchase purchase = purchaseService.getPurchaseById(id, auth.getName());
            model.addAttribute("purchase", purchase);
            return "ticket/view";
        } catch (RuntimeException e) {
            return "redirect:/purchases/my-purchases?error=" + e.getMessage();
        }
    }

    @PostMapping("/cancel/{id}")
    public String cancelTicket(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getName().equals("anonymousUser")) {
            return "redirect:/auth/login";
        }

        try {
            purchaseService.cancelPurchase(id, auth.getName());
            redirectAttributes.addFlashAttribute("successMessage",
                    "خرید با موفقیت لغو شد.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/purchases/my-purchases";
    }
}