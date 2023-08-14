package tritronik.test.SmartHomeStay.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tritronik.test.SmartHomeStay.api.request.PaymentAccountRequest;
import tritronik.test.SmartHomeStay.api.request.PaymentRequest;
import tritronik.test.SmartHomeStay.entity.Payment;
import tritronik.test.SmartHomeStay.entity.PaymentAccount;
import tritronik.test.SmartHomeStay.service.PaymentService;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @GetMapping("/accounts")
    public List<PaymentAccount> getAllPaymentAccounts() {
        return paymentService.getAllPaymentAccounts();
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @PostMapping("/add")
    public ResponseEntity<Object> addAccount(@RequestBody PaymentAccountRequest paymentAccountRequest) throws Exception {
        if (paymentService.addPaymentAccount(paymentAccountRequest) != null) {
            return ResponseEntity.ok(Map.of("message" , "Payment account added!"));
        }
        return new ResponseEntity<>(Map.of("message", "Failed to add payment account!"), HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @GetMapping("")
    public List<Payment> getAllPayments() throws Exception {
       return paymentService.getAllPayments();
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @PostMapping("/confirm")
    public ResponseEntity<Object> confirmPayment(@RequestParam Long id) throws Exception {
        Payment payment = paymentService.confirm(id);
        return ResponseEntity.ok(Map.of("message" , "Payment confirmed by you!", "data", payment));
    }

    
    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @PostMapping("/pay")
    public ResponseEntity<Object> pay(@RequestBody PaymentRequest paymentRequest) throws Exception {
        if (paymentService.pay(paymentRequest) != null) {
            return ResponseEntity.ok(Map.of("message" , "You send the payment. Let the admin check for you"));
        }
        return new ResponseEntity<>(Map.of("message", "Failed to pay"), HttpStatus.BAD_REQUEST);
    }
}
