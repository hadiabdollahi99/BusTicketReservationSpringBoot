package ir.maktabsharif.busticketreservationspringboot.controller;

import ir.maktabsharif.busticketreservationspringboot.model.Purchase;
import ir.maktabsharif.busticketreservationspringboot.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/my-tickets")
@RequiredArgsConstructor
public class MyTicketsController {

    private final TicketService ticketService;

    @GetMapping
    public String myTickets(Model model) {
        List<Purchase> purchases = ticketService.getUserPurchases();
        model.addAttribute("purchases", purchases);
        return "my-tickets";
    }


    @GetMapping("/details/{purchaseId}")
    public String ticketDetails(@PathVariable Long purchaseId, Model model) {
        try {
            Purchase purchase = ticketService.getPurchaseById(purchaseId);
            model.addAttribute("purchase", purchase);
            return "ticket-details";
        } catch (Exception e) {
            return "redirect:/my-tickets?error=" + e.getMessage();
        }
    }

    @PostMapping("/cancel/{purchaseId}")
    public String cancelPurchase(@PathVariable Long purchaseId,
                                 RedirectAttributes redirectAttributes) {
        try {
            ticketService.cancelPurchase(purchaseId);
            redirectAttributes.addFlashAttribute("success",
                    "بلیط با موفقیت لغو شد!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "خطا در لغو بلیط: " + e.getMessage());
        }
        return "redirect:/my-tickets";
    }

}