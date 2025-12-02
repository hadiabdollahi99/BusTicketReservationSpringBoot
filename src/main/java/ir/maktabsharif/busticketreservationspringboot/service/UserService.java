package ir.maktabsharif.busticketreservationspringboot.service;

import ir.maktabsharif.busticketreservationspringboot.model.User;
import ir.maktabsharif.busticketreservationspringboot.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initUsers() {
        if (userRepository.count() == 0) {
            User user = User.builder()
                    .username("user")
                    .password(passwordEncoder.encode("password"))
                    .fullName("کاربر تستی")
                    .enabled(true)
                    .build();

            userRepository.save(user);
        }
    }
}