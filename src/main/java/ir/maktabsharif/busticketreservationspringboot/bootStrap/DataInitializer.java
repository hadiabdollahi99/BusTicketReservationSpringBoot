package ir.maktabsharif.busticketreservationspringboot.bootStrap;

import ir.maktabsharif.busticketreservationspringboot.model.Role;
import ir.maktabsharif.busticketreservationspringboot.model.Ticket;
import ir.maktabsharif.busticketreservationspringboot.model.User;
import ir.maktabsharif.busticketreservationspringboot.repository.RoleRepository;
import ir.maktabsharif.busticketreservationspringboot.repository.TicketRepository;
import ir.maktabsharif.busticketreservationspringboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TicketRepository ticketRepository;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            Role adminRole = Role.builder()
                    .name("ADMIN")
                    .build();

            Role userRole = Role.builder()
                    .name("USER")
                    .build();

            roleRepository.saveAll(List.of(adminRole, userRole));
        }


        if (userRepository.count() == 0) {
            Role adminRole = roleRepository.findByName("ADMIN")
                    .orElseThrow(() -> new RuntimeException("Role ADMIN not found"));

            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .fullName("مدیر سیستم")
                    .enabled(true)
                    .role(adminRole)
                    .build();

            userRepository.save(admin);
        }

        if (ticketRepository.count() == 0) {
            Ticket ticket1 = Ticket.builder()
                    .departureCity("Tehran")
                    .destinationCity("Mashhad")
                    .departureDate(LocalDate.now().plusDays(1))
                    .departureTime(LocalTime.of(8, 0))
                    .tripNumber("THR-MHD-001")
                    .build();

            Ticket ticket2 = Ticket.builder()
                    .departureCity("Tehran")
                    .destinationCity("Mashhad")
                    .departureDate(LocalDate.now().plusDays(1))
                    .departureTime(LocalTime.of(14, 30))
                    .tripNumber("THR-MHD-002")
                    .build();

            Ticket ticket3 = Ticket.builder()
                    .departureCity("Tehran")
                    .destinationCity("Isfahan")
                    .departureDate(LocalDate.now().plusDays(2))
                    .departureTime(LocalTime.of(10, 0))
                    .tripNumber("THR-ESF-001")
                    .build();

            ticketRepository.saveAll(List.of(ticket1, ticket2, ticket3));
        }

//        Ticket ticket1 = Ticket.builder()
//                .admin(admin)
//                .departureCity("Tehran")
//                .destinationCity("Mashhad")
//                .departureDate(LocalDate.parse("2024-01-15"))
//                .departureTime(LocalTime.parse("08:00:00"))
//                .tripNumber("245")
//                .build();
//        Ticket ticket2 = Ticket.builder()
//                .admin(admin)
//                .departureCity("Tehran")
//                .destinationCity("Mashhad")
//                .departureDate(LocalDate.parse("2024-01-15"))
//                .departureTime(LocalTime.parse("12:30:00"))
//                .tripNumber("345")
//                .build();
//        Ticket ticket3 = Ticket.builder()
//                .admin(admin)
//                .departureCity("Tehran")
//                .destinationCity("Mashhad")
//                .departureDate(LocalDate.parse("2024-01-15"))
//                .departureTime(LocalTime.parse("18:45:00"))
//                .tripNumber("442")
//                .build();
//        Ticket ticket4 = Ticket.builder()
//                .admin(admin)
//                .departureCity("Tehran")
//                .destinationCity("Isfahan")
//                .departureDate(LocalDate.parse("2024-01-15"))
//                .departureTime(LocalTime.parse("07:30:00"))
//                .tripNumber("309")
//                .build();
//        Ticket ticket5 = Ticket.builder()
//                .admin(admin)
//                .departureCity("Tehran")
//                .destinationCity("Isfahan")
//                .departureDate(LocalDate.parse("2024-01-15"))
//                .departureTime(LocalTime.parse("14:00:00"))
//                .tripNumber("879")
//                .build();
//        Ticket ticket6 = Ticket.builder()
//                .admin(admin)
//                .departureCity("Mashhad")
//                .destinationCity("Tehran")
//                .departureDate(LocalDate.parse("2024-01-16"))
//                .departureTime(LocalTime.parse("09:15:00"))
//                .tripNumber("123")
//                .build();
//        Ticket ticket7 = Ticket.builder()
//                .admin(admin)
//                .departureCity("Isfahan")
//                .destinationCity("Tehran")
//                .departureDate(LocalDate.parse("2024-01-16"))
//                .departureTime(LocalTime.parse("16:20:00"))
//                .tripNumber("765")
//                .build();
//        ticketRepository.save(ticket1);
//        ticketRepository.save(ticket2);
//        ticketRepository.save(ticket3);
//        ticketRepository.save(ticket4);
//        ticketRepository.save(ticket5);
//        ticketRepository.save(ticket6);
//        ticketRepository.save(ticket7);
//
//    }
    }
}