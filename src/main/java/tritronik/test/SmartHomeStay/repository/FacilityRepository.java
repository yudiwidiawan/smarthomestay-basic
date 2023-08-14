package tritronik.test.SmartHomeStay.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import tritronik.test.SmartHomeStay.entity.Facility;


@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {
    
}
