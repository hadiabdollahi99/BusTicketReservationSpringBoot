package ir.maktabsharif.busticketreservationspringboot.repository;


import ir.maktabsharif.busticketreservationspringboot.model.Purchase;
import ir.maktabsharif.busticketreservationspringboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<Purchase,Long> {
    List<Purchase> findByUserOrderByCreatedAtDesc(User user);
    Optional<Purchase> findByTicketNumber(String ticketNumber);
    Boolean existsByTicketNumber(String ticketNumber);

    @Query("SELECT p FROM Purchase p WHERE p.user = :user AND p.ticket.id = :ticketId")
    List<Purchase> findPurchaseByUserAndTicketId(@Param("user") User user, @Param("ticketId") Long ticketId);

}
