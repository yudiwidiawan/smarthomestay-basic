package tritronik.test.SmartHomeStay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tritronik.test.SmartHomeStay.service.PaymentService;
import tritronik.test.SmartHomeStay.service.ReservationService;
import tritronik.test.SmartHomeStay.api.request.CheckInRequest;
import tritronik.test.SmartHomeStay.api.request.ReservationRequest;
import tritronik.test.SmartHomeStay.entity.Reservation;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    @Autowired
    ReservationService reservationService;

    @Autowired
    PaymentService paymentService;

    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }
    
    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @GetMapping("/self")
    public List<Reservation> getSelfReservations() {
        return reservationService.getSelfReservations();
    }

    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @PostMapping("/reserve")
    public ResponseEntity<Reservation> addNewReservation(@RequestBody ReservationRequest reservationRequest) throws Exception {
        return ResponseEntity.ok(reservationService.addReservation(reservationRequest));
    }

    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @PostMapping("/check-in")
    public ResponseEntity<Object> checkIn(@RequestBody CheckInRequest checkInRequest) throws Exception {
        if (reservationService.checkIn(checkInRequest) != null) {
            return ResponseEntity.ok(Map.of("message" , "Checked In"));
        }
        return new ResponseEntity<>(Map.of("message", "Failed to check in"), HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @PostMapping("/check-out")
    public ResponseEntity<Object> checkOut(@RequestBody CheckInRequest checkInRequest) throws Exception {
        if (reservationService.checkOut(checkInRequest) != null) {
            return ResponseEntity.ok(Map.of("message" , "Checked Out"));
        }
        return new ResponseEntity<>(Map.of("message", "Failed to check out"), HttpStatus.BAD_REQUEST);
    }

}