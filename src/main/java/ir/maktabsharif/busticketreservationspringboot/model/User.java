package ir.maktabsharif.busticketreservationspringboot.model;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "Users")
public class User extends BaseEntity<Long> implements Serializable {
    private String username;
    private String email;
    private String password;

    private boolean enabled = true;

    @ManyToOne
    private Role role;

    @ToString.Exclude
    @OneToMany(mappedBy = "admin") //, cascade = CascadeType.PERSIST
    private List<Ticket> tickets = new ArrayList<>();


    @ToString.Exclude
    @OneToMany(mappedBy = "user") //, cascade = CascadeType.PERSIST
    private List<Purchase> purchases = new ArrayList<>();


}