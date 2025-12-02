package ir.maktabsharif.busticketreservationspringboot.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "Purchases")
public class Purchase extends BaseEntity<Long> implements Serializable {

    private String customerName;
    @Enumerated(value = EnumType.STRING)
    private Gender customerGender;
    private String ticketNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_ticket")
    private Ticket ticket;
}
