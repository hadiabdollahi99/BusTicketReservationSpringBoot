package ir.maktabsharif.busticketreservationspringboot.service;


import ir.maktabsharif.busticketreservationspringboot.model.Role;
import ir.maktabsharif.busticketreservationspringboot.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }
}
