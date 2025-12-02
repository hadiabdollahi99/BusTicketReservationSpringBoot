package ir.maktabsharif.busticketreservationspringboot.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "Tickets")
public class Ticket extends BaseEntity<Long> implements Serializable {

    private String departureCity;
    private String destinationCity;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private String tripNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_admin")
    private User admin;

    @ToString.Exclude
    @OneToMany(mappedBy = "ticket")  //, cascade = {CascadeType.PERSIST,CascadeType.REMOVE}
    private List<Purchase> sold = new ArrayList<>();
}
