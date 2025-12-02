package ir.maktabsharif.busticketreservationspringboot.service;

import ir.maktabsharif.busticketreservationspringboot.dto.UserRegisterDto;
import ir.maktabsharif.busticketreservationspringboot.model.Role;
import ir.maktabsharif.busticketreservationspringboot.model.User;
import ir.maktabsharif.busticketreservationspringboot.repository.RoleRepository;
import ir.maktabsharif.busticketreservationspringboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(UserRegisterDto dto) {

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("نام کاربری قبلاً ثبت شده است");
        }

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("ایمیل قبلاً ثبت شده است");
        }

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> {
                    Role newRole = Role.builder()
                            .name("ROLE_USER").build();
                    return roleRepository.save(newRole);
                });

        User user = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .enabled(true)
                .role(userRole).build();

        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("کاربر یافت نشد"));
    }

    public User getCurrentUser() {
        // This will be populated by Spring Security
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User)
                        org.springframework.security.core.context.SecurityContextHolder
                                .getContext().getAuthentication().getPrincipal();

        return findByUsername(principal.getUsername());
    }
}