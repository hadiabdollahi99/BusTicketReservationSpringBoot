package ir.maktabsharif.busticketreservationspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketSearchDto {
    private String departureCity;
    private String destinationCity;
    private LocalDate departureDate;
}