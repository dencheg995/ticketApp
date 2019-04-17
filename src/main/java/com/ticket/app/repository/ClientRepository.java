package com.ticket.app.repository;



import com.ticket.app.module.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClientRepository extends JpaRepository<AppUser, Long> {

    AppUser getClientByEmailOrPhoneNumberOrVkToken(String email, String phoneNumber, String vkToken);

    AppUser getClientByEmail(String email);

    @Query("SELECT client FROM AppUser client JOIN client.events events WHERE events.id = :eventId")
    AppUser getClientByEventId(@Param("eventId") Long eventId);

}
