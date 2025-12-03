package ir.maktabsharif.busticketreservationspringboot.controller;

import ir.maktabsharif.busticketreservationspringboot.dto.PurchaseDto;
import ir.maktabsharif.busticketreservationspringboot.dto.SearchDto;
import ir.maktabsharif.busticketreservationspringboot.model.Purchase;
import ir.maktabsharif.busticketreservationspringboot.model.Ticket;
import ir.maktabsharif.busticketreservationspringboot.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

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

}