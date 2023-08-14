package tritronik.test.SmartHomeStay.repository;

import org.springframework.stereotype.Repository;

import tritronik.test.SmartHomeStay.entity.Payment;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{
    
}
