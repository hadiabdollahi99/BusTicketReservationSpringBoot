package ir.maktabsharif.busticketreservationspringboot.controller;

import ir.maktabsharif.busticketreservationspringboot.dto.PurchaseDto;
import ir.maktabsharif.busticketreservationspringboot.dto.SearchDto;
import ir.maktabsharif.busticketreservationspringboot.model.Purchase;
import ir.maktabsharif.busticketreservationspringboot.model.Ticket;
import ir.maktabsharif.busticketreservationspringboot.service.PurchaseService;
import ir.maktabsharif.busticketreservationspringboot.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final TicketService ticketService;
    private final PurchaseService purchaseService;

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) {
        if (error != null) {
            model.addAttribute("error", "نام کاربری یا کلمه عبور نادرست است!");
        }
        if (logout != null) {
            model.addAttribute("message", "با موفقیت خارج شدید.");
        }
        return "login";
    }

    @GetMapping("/search")
    public String searchPage(Model model) {
        model.addAttribute("searchDto", new SearchDto());
        return "search";
    }

    @PostMapping("/search")
    public String search(@ModelAttribute SearchDto searchDto, Model model) {
        List<Ticket> tickets = ticketService.searchTickets(searchDto);
        model.addAttribute("tickets", tickets);
        model.addAttribute("searchDto", searchDto);
        return "search";
    }

    @GetMapping("/purchase/{ticketId}")
    public String purchaseForm(@PathVariable Long ticketId, Model model) {
        PurchaseDto purchaseDto = new PurchaseDto();
        purchaseDto.setTicketId(ticketId);
        model.addAttribute("purchaseDto", purchaseDto);
        return "purchase";
    }

    @PostMapping("/purchase")
    public String purchase(@ModelAttribute PurchaseDto purchaseDto,
                           RedirectAttributes redirectAttributes) {
        try {
            Purchase purchase = ticketService.purchaseTicket(purchaseDto);
            String gender;
            if (purchase.getGender().equals("MALE")){
                gender = "آقای";
            } else {
                gender = "خانم";
            }
            redirectAttributes.addFlashAttribute("success",gender + " " + purchase.getCustomerName() + "،" +
                    " خرید بلیط شما با موفقیت انجام شد! شناسه بلیط: " + purchase.getTicketNumber());
            redirectAttributes.addFlashAttribute("ticketNumber",
                    purchase.getTicketNumber());
            return "redirect:/confirmation";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "خطا در خرید بلیط: " + e.getMessage());
            return "redirect:/purchase/" + purchaseDto.getTicketId();
        }
    }

    @GetMapping("/confirmation")
    public String confirmationPage() {
        return "confirmation";
    }

    @GetMapping("/my-tickets")
    public String myTickets(Model model) {
        List<Purchase> purchases = ticketService.getUserPurchases();
        model.addAttribute("purchases", purchases);
        return "my-tickets";
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

    @GetMapping("/ticket-details/{purchaseId}")
    public String ticketDetails(@PathVariable Long purchaseId, Model model) {
        try {
            Purchase purchase = purchaseService.findById(purchaseId)
                    .orElseThrow(() -> new RuntimeException("بلیط یافت نشد"));

            // بررسی مالکیت بلیط
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();

            if (!purchase.getUser().getUsername().equals(username)) {
                throw new RuntimeException("شما مجاز به مشاهده این بلیط نیستید");
            }

            model.addAttribute("purchase", purchase);
            return "ticket-details";

        } catch (Exception e) {
            return "redirect:/my-tickets?error=" + e.getMessage();
        }
    }

}