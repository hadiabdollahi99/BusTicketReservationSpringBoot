package ir.maktabsharif.busticketreservationspringboot.controller;

import ir.maktabsharif.busticketreservationspringboot.dto.TicketSearchDto;
import ir.maktabsharif.busticketreservationspringboot.model.Ticket;
import ir.maktabsharif.busticketreservationspringboot.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final TicketService ticketService;

    @GetMapping
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            return "redirect:/dashboard";
        }

        List<Ticket> upcomingTickets = ticketService.getUpcomingTickets();
        model.addAttribute("upcomingTrips", upcomingTickets);
        model.addAttribute("searchDto", new TicketSearchDto());

        return "home/index";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getName().equals("anonymousUser")) {
            return "redirect:/auth/login";
        }

        model.addAttribute("username", auth.getName());
        model.addAttribute("searchDto", new TicketSearchDto());

        return "home/dashboard";
    }

    @PostMapping("/search")
    public String searchTickets(@ModelAttribute TicketSearchDto searchDto, Model model) {
        List<Ticket> tickets = ticketService.searchTickets(searchDto);
        model.addAttribute("trips", tickets);
        model.addAttribute("searchDto", searchDto);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            return "ticket/search-results";
        } else {
            return "home/search-results";
        }
    }
}