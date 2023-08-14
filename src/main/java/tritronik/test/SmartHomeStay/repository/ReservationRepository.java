package tritronik.test.SmartHomeStay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tritronik.test.SmartHomeStay.entity.Reservation;
import tritronik.test.SmartHomeStay.entity.User;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // Custom methods for reservation repository, if needed

    List<Reservation> findByUser(User user);
}