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

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TicketRepository ticketRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Role roleAdmin = Role.builder()
                .name("ROLE_ADMIN")
                .build();
        roleRepository.save(roleAdmin);

        Role roleUser = Role.builder()
                .name("ROLE_USER")
                .build();
        roleRepository.save(roleUser);

        String encodeAdmin = passwordEncoder.encode("123456");
        User admin = User.builder()
                .username("admin")
                .password(encodeAdmin)
                .email("admin@email.com")
                .role(roleAdmin)
                .build();

        String encodeUser = passwordEncoder.encode("1234");
        User user = User.builder()
                .username("hadi")
                .password(encodeUser)
                .email("hadi@email.com")
                .role(roleUser)
                .build();


        userRepository.save(admin);
        userRepository.save(user);


        Ticket ticket1 = Ticket.builder()
                .admin(admin)
                .departureCity("Tehran")
                .destinationCity("Mashhad")
                .departureDate(LocalDate.parse("2024-01-15"))
                .departureTime(LocalTime.parse("08:00:00"))
                .tripNumber("245")
                .build();
        Ticket ticket2 = Ticket.builder()
                .admin(admin)
                .departureCity("Tehran")
                .destinationCity("Mashhad")
                .departureDate(LocalDate.parse("2024-01-15"))
                .departureTime(LocalTime.parse("12:30:00"))
                .tripNumber("345")
                .build();
        Ticket ticket3 = Ticket.builder()
                .admin(admin)
                .departureCity("Tehran")
                .destinationCity("Mashhad")
                .departureDate(LocalDate.parse("2024-01-15"))
                .departureTime(LocalTime.parse("18:45:00"))
                .tripNumber("442")
                .build();
        Ticket ticket4 = Ticket.builder()
                .admin(admin)
                .departureCity("Tehran")
                .destinationCity("Isfahan")
                .departureDate(LocalDate.parse("2024-01-15"))
                .departureTime(LocalTime.parse("07:30:00"))
                .tripNumber("309")
                .build();
        Ticket ticket5 = Ticket.builder()
                .admin(admin)
                .departureCity("Tehran")
                .destinationCity("Isfahan")
                .departureDate(LocalDate.parse("2024-01-15"))
                .departureTime(LocalTime.parse("14:00:00"))
                .tripNumber("879")
                .build();
        Ticket ticket6 = Ticket.builder()
                .admin(admin)
                .departureCity("Mashhad")
                .destinationCity("Tehran")
                .departureDate(LocalDate.parse("2024-01-16"))
                .departureTime(LocalTime.parse("09:15:00"))
                .tripNumber("123")
                .build();
        Ticket ticket7 = Ticket.builder()
                .admin(admin)
                .departureCity("Isfahan")
                .destinationCity("Tehran")
                .departureDate(LocalDate.parse("2024-01-16"))
                .departureTime(LocalTime.parse("16:20:00"))
                .tripNumber("765")
                .build();
        ticketRepository.save(ticket1);
        ticketRepository.save(ticket2);
        ticketRepository.save(ticket3);
        ticketRepository.save(ticket4);
        ticketRepository.save(ticket5);
        ticketRepository.save(ticket6);
        ticketRepository.save(ticket7);

    }
}