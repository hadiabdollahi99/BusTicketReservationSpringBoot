package ir.maktabsharif.busticketreservationspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchDto {
    private String departureCity;
    private String destinationCity;
    private LocalDate departureDate;
}