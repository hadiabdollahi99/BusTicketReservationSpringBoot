//package ir.maktabsharif.busticketreservationspringboot.controller;
//
//import ir.maktabsharif.busticketreservationspringboot.dto.PurchaseDto;
//import ir.maktabsharif.busticketreservationspringboot.model.Purchase;
//import ir.maktabsharif.busticketreservationspringboot.model.Ticket;
//import ir.maktabsharif.busticketreservationspringboot.model.User;
//import ir.maktabsharif.busticketreservationspringboot.service.TicketService;
//import ir.maktabsharif.busticketreservationspringboot.service.UserService;
//import jakarta.servlet.http.HttpSession;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//import java.util.Optional;
//
//@Controller
//@RequestMapping("/ticket")
//@RequiredArgsConstructor
//public class TicketController {
//
//    private final TicketService ticketService;
//    private final PurchaseService purchaseService;
//    private final UserService userService;
//
//    @GetMapping("/buy/{ticketId}")
//    public String showBuyForm(@PathVariable Long ticketId,
//                              Model model,
//                              HttpSession session,
//                              RedirectAttributes redirectAttributes) {
//
//        if (session.getAttribute("userId") == null) {
//            return "redirect:/auth/login";
//        }
//
//        Optional<Ticket> ticketOpt = ticketService.findById(ticketId);
//        if (ticketOpt.isEmpty()) {
//            redirectAttributes.addFlashAttribute("error", "سفر مورد نظر یافت نشد");
//            return "redirect:/search";
//        }
//
//        PurchaseDto purchaseDto = new PurchaseDto();
//        purchaseDto.setTicketId(ticketId);
//
//        model.addAttribute("purchaseDto", purchaseDto);
//        model.addAttribute("ticket", ticketOpt.get());
//
//        return "buy-ticket";
//    }
//
//    @PostMapping("/buy")
//    public String buyTicket(@ModelAttribute PurchaseDto purchaseDto,
//                            HttpSession session,
//                            RedirectAttributes redirectAttributes) {
//
//        if (session.getAttribute("userId") == null) {
//            return "redirect:/auth/login";
//        }
//
//        String username = (String) session.getAttribute("username");
//        Optional<User> userOpt = userService.findByUsername(username);
//        Optional<Ticket> ticketOpt = ticketService.findById(purchaseDto.getTicketId());
//
//        if (userOpt.isEmpty() || ticketOpt.isEmpty()) {
//            redirectAttributes.addFlashAttribute("error", "خطا در پردازش درخواست");
//            return "redirect:/search";
//        }
//
//        Purchase purchase = purchaseService.createPurchase(purchaseDto, userOpt.get(), ticketOpt.get());
//
//        String genderTitle = purchaseService.getGenderTitle(purchase.getGender());
//        String fullNameWithTitle = genderTitle + " " + purchase.getCustomerName();
//
//        redirectAttributes.addFlashAttribute("success", "خرید بلیط با موفقیت انجام شد");
//        redirectAttributes.addFlashAttribute("ticketNumber", purchase.getTicketNumber());
//        redirectAttributes.addFlashAttribute("fullNameWithTitle", fullNameWithTitle);
//        redirectAttributes.addFlashAttribute("purchase", purchase);
//
//        return "redirect:/ticket/confirmation";
//    }
//
//    @GetMapping("/confirmation")
//    public String showConfirmation(Model model, HttpSession session) {
//        if (session.getAttribute("userId") == null) {
//            return "redirect:/auth/login";
//        }
//
//        if (!model.containsAttribute("purchase")) {
//            return "redirect:/search";
//        }
//
//        return "confirmation";
//    }
//
//    @GetMapping("/my-tickets")
//    public String showMyTickets(Model model, HttpSession session) {
//        if (session.getAttribute("userId") == null) {
//            return "redirect:/auth/login";
//        }
//
//        String username = (String) session.getAttribute("username");
//        Optional<User> userOpt = userService.findByUsername(username);
//
//        if (userOpt.isEmpty()) {
//            return "redirect:/auth/login";
//        }
//
//        var purchases = purchaseService.getUserPurchases(userOpt.get());
//        model.addAttribute("purchases", purchases);
//
//        return "my-tickets";
//    }
//
//    @GetMapping("/view/{ticketNumber}")
//    public String viewTicket(@PathVariable String ticketNumber,
//                             Model model,
//                             HttpSession session) {
//
//        if (session.getAttribute("userId") == null) {
//            return "redirect:/auth/login";
//        }
//
//        Optional<Purchase> purchaseOpt = purchaseService.findByTicketNumber(ticketNumber);
//        if (purchaseOpt.isEmpty()) {
//            model.addAttribute("error", "بلیط مورد نظر یافت نشد");
//            return "my-tickets";
//        }
//
//        Purchase purchase = purchaseOpt.get();
//        String genderTitle = purchaseService.getGenderTitle(purchase.getGender());
//        String fullNameWithTitle = genderTitle + " " + purchase.getCustomerName();
//
//        model.addAttribute("purchase", purchase);
//        model.addAttribute("fullNameWithTitle", fullNameWithTitle);
//
//        return "view-ticket";
//    }
//
//    @PostMapping("/cancel/{ticketNumber}")
//    public String cancelTicket(@PathVariable String ticketNumber,
//                               HttpSession session,
//                               RedirectAttributes redirectAttributes) {
//
//        if (session.getAttribute("userId") == null) {
//            return "redirect:/auth/login";
//        }
//
//        boolean cancelled = purchaseService.cancelPurchase(ticketNumber);
//
//        if (cancelled) {
//            redirectAttributes.addFlashAttribute("success", "بلیط با موفقیت لغو شد");
//        } else {
//            redirectAttributes.addFlashAttribute("error", "خطا در لغو بلیط");
//        }
//
//        return "redirect:/ticket/my-tickets";
//    }
//}