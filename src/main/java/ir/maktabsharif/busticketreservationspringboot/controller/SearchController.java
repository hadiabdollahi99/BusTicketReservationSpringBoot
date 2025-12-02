//package ir.maktabsharif.busticketreservationspringboot.controller;
//
//import ir.maktabsharif.busticketreservationspringboot.dto.SearchDto;
//import ir.maktabsharif.busticketreservationspringboot.model.Ticket;
//import ir.maktabsharif.busticketreservationspringboot.service.TicketService;
//import jakarta.servlet.http.HttpSession;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@Controller
//@RequestMapping("/search")
//@RequiredArgsConstructor
//public class SearchController {
//
//    private final TicketService ticketService;
//
//    @GetMapping
//    public String showSearchForm(Model model, HttpSession session) {
//        if (session.getAttribute("userId") == null) {
//            return "redirect:/auth/login";
//        }
//
//        SearchDto searchDto = new SearchDto();
//        searchDto.setDepartureDate(LocalDate.now().plusDays(1));
//        model.addAttribute("searchDto", searchDto);
//
//        return "search";
//    }
//
//    @PostMapping
//    public String search(@ModelAttribute SearchDto searchDto,
//                         Model model,
//                         HttpSession session) {
//
//        if (session.getAttribute("userId") == null) {
//            return "redirect:/auth/login";
//        }
//
//        List<Ticket> tickets = ticketService.searchTickets(
//                searchDto.getDepartureCity(),
//                searchDto.getDestinationCity(),
//                searchDto.getDepartureDate()
//        );
//
//        model.addAttribute("tickets", tickets);
//        model.addAttribute("searchDto", searchDto);
//
//        return "search-results";
//    }
//}