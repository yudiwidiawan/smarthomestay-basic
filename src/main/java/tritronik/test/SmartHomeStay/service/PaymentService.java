package tritronik.test.SmartHomeStay.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import tritronik.test.SmartHomeStay.api.request.PaymentAccountRequest;
import tritronik.test.SmartHomeStay.api.request.PaymentRequest;
import tritronik.test.SmartHomeStay.entity.Payment;
import tritronik.test.SmartHomeStay.entity.PaymentAccount;
import tritronik.test.SmartHomeStay.entity.Reservation;
import tritronik.test.SmartHomeStay.entity.User;
import tritronik.test.SmartHomeStay.repository.PaymentAccountRepository;
import tritronik.test.SmartHomeStay.repository.PaymentRepository;
import tritronik.test.SmartHomeStay.repository.ReservationRepository;
import tritronik.test.SmartHomeStay.repository.RoomRepository;
import tritronik.test.SmartHomeStay.repository.UserRepository;

@Service
public class PaymentService {
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    PaymentAccountRepository paymentAccountRepository;

    public List<PaymentAccount> getAllPaymentAccounts() {
        return paymentAccountRepository.findAll();
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public PaymentAccount addPaymentAccount(PaymentAccountRequest paymentAccountRequest) {
        PaymentAccount newPaymentAccount = new PaymentAccount();
        newPaymentAccount.setName(paymentAccountRequest.getName());
        newPaymentAccount.setAccountNumber(paymentAccountRequest.getAccountNumber());
        return paymentAccountRepository.save(newPaymentAccount);
    }

    public Payment confirm(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName()).get();
        Payment payment = paymentRepository.findById(id).get();
        payment.setStatus("CONFIRMED");
        payment.setVerifier(user);
        return paymentRepository.save(payment);
    }

    public Payment pay(PaymentRequest paymentRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName()).get();
        Reservation reservation = reservationRepository.findById(paymentRequest.getReservationId()).get();
        if (reservation.getStatus().equals("BOOKED") && reservation.getPayment() == null && reservation.getUser() == user) {
            Payment payment = new Payment();
            payment.setType(paymentRequest.getType());
            payment.setTransferProof(paymentRequest.getTransferProof());
            long diffInMillies = Math.abs(reservation.getStartDate().getTime() - reservation.getEndDate().getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            payment.setTotalPayment(diff * reservation.getRoom().getPrice());
            payment.setStatus("ON_CHECK");
            reservation.setPayment(payment);
            return paymentRepository.save(payment);
        }
        return null;
    }
}
