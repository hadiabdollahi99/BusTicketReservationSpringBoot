package ir.maktabsharif.busticketreservationspringboot.dto;


import ir.maktabsharif.busticketreservationspringboot.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketPurchaseDto {
    private Long ticketId;
    private String customerName;
    private Gender gender;

}