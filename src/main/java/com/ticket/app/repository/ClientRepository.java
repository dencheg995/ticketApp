package com.ticket.app.repository;



import com.ticket.app.module.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClientRepository extends JpaRepository<AppUser, Long> {

    AppUser getByEmailOrPhoneNumberOrVkId(String email, String phone, String vkId);

    AppUser getByVkId(String vkId);

    AppUser getClientByEmail(String email);

    @Query("SELECT client FROM AppUser client JOIN client.events events WHERE events.id = :eventId")
    AppUser getClientByEventId(@Param("eventId") Long eventId);

}
