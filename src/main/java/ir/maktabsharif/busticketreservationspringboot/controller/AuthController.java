//package ir.maktabsharif.busticketreservationspringboot.controller;
//
//import ir.maktabsharif.busticketreservationspringboot.model.User;
//import ir.maktabsharif.busticketreservationspringboot.service.RoleService;
//import ir.maktabsharif.busticketreservationspringboot.service.UserService;
//import jakarta.servlet.http.HttpSession;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//@Controller
//@RequestMapping("/auth")
//@RequiredArgsConstructor
//public class AuthController {
//
//    private final UserService userService;
//    private final RoleService roleService;
//
//    @GetMapping("/login")
//    public String showLoginForm(Model model) {
//        model.addAttribute("loginDto", new LoginDto());
//        return "login";
//    }
//
//    @PostMapping("/login")
//    public String login(@ModelAttribute LoginDto loginDto,
//                        HttpSession session,
//                        RedirectAttributes redirectAttributes) {
//
//        if (userService.authenticate(loginDto.getUsername(), loginDto.getPassword())) {
//            userService.findByUsername(loginDto.getUsername()).ifPresent(user -> {
//                session.setAttribute("user", user);
//                session.setAttribute("userId", user.getId());
//                session.setAttribute("username", user.getUsername());
//                session.setAttribute("fullName", user.getFullName());
//                session.setAttribute("role", user.getRole() != null ? user.getRole().getName() : "USER");
//            });
//            return "redirect:/search";
//        }
//
//        redirectAttributes.addFlashAttribute("error", "نام کاربری یا رمز عبور اشتباه است");
//        return "redirect:/auth/login";
//    }
//
//    @GetMapping("/register")
//    public String showRegisterForm(Model model) {
//        model.addAttribute("user", new User());
//        return "register";
//    }
//
//    @PostMapping("/register")
//    public String register(@ModelAttribute User user,
//                           RedirectAttributes redirectAttributes) {
//
//        if (userService.userExists(user.getUsername())) {
//            redirectAttributes.addFlashAttribute("error", "نام کاربری قبلاً استفاده شده است");
//            return "redirect:/auth/register";
//        }
//
//        roleService.findByName("ROLE_USER").ifPresent(user::setRole);
//
//        userService.save(user);
//
//        redirectAttributes.addFlashAttribute("success", "ثبت‌نام با موفقیت انجام شد. لطفاً وارد شوید.");
//        return "redirect:/auth/login";
//    }
//
//    @GetMapping("/logout")
//    public String logout(HttpSession session) {
//        session.invalidate();
//        return "redirect:/auth/login";
//    }
//}