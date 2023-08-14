package tritronik.test.SmartHomeStay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import tritronik.test.SmartHomeStay.kafka.bean.NotificationCheckIn;
import tritronik.test.SmartHomeStay.repository.FacilityRepository;
import tritronik.test.SmartHomeStay.repository.ReservationRepository;
import tritronik.test.SmartHomeStay.repository.RoomRepository;
import tritronik.test.SmartHomeStay.repository.UserRepository;
import tritronik.test.SmartHomeStay.api.request.CheckInRequest;
import tritronik.test.SmartHomeStay.api.request.ReservationRequest;
import tritronik.test.SmartHomeStay.entity.Reservation;
import tritronik.test.SmartHomeStay.entity.Room;
import tritronik.test.SmartHomeStay.entity.User;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ReservationService {

    private static final String TOPIC = "reservations";
    private static final String TOPIC_CHECKIN = "checkins";

    private static final String TOPIC_CHECKOUT = "checkouts";
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoomRepository roomRepository;

    @Autowired
    FacilityRepository facilityRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }
    
    public List<Reservation> getSelfReservations() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName()).get();
        return reservationRepository.findByUser(user);
    }

    public Reservation addReservation(ReservationRequest reservationRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName()).get();
        Room room = roomRepository.findById(reservationRequest.getRoomId()).get();
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setRoom(room);
        reservation.setStartDate(reservationRequest.getStartDate());
        reservation.setEndDate(reservationRequest.getEndDate());
        reservation.setStatus("BOOKED");
        reservation.setFacilities(reservationRequest.getFacilities());
        kafkaTemplate.send(TOPIC, String.format("Booking Kamar %d untuk Tuan/Nyonya %s", room.getId(), user.getLast_name()));
        return reservationRepository.save(reservation);
    }
    
    public Reservation checkIn(CheckInRequest checkInRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName()).get();
        Reservation reservation = reservationRepository.findById(checkInRequest.getReservationId()).get();
        if (reservation.getPayment().getStatus().equals("CONFIRMED") && reservation.getPayment() != null && reservation.getUser() == user) {
            reservation.setCheckInTime(new Timestamp(System.currentTimeMillis()));
            reservation.setStatus("CHECKED_IN");
            Room room = reservation.getRoom();
            room.setStatus("UNAVAILABLE");
            roomRepository.save(room);
            kafkaTemplate.send(TOPIC_CHECKIN, new NotificationCheckIn(
                    "Kamar " + room.getId(),
                    user.getLast_name(),
                    reservation.getCheckInTime()
            ).toString());
//            kafkaTemplate.send(TOPIC_CHECKIN, String.format("Kamar %d Tuan/Nyonya %s telah check-in", room.getId(), user.getLast_name()));
            return reservationRepository.save(reservation);
        }
        return null;
    }

    public Reservation checkOut(CheckInRequest checkInRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName()).get();
        Reservation reservation = reservationRepository.findById(checkInRequest.getReservationId()).get();
        if (reservation.getPayment().getStatus().equals("CONFIRMED") && reservation.getPayment() != null && reservation.getUser() == user && reservation.getStatus().equals("CHECKED_IN")) {
            reservation.setCheckInTime(new Timestamp(System.currentTimeMillis()));
            reservation.setStatus("CHECKED_OUT");
            Room room = reservation.getRoom();
            room.setStatus("AVAILABLE");
            roomRepository.save(room);
            kafkaTemplate.send(TOPIC_CHECKOUT, String.format("Kamar %d Tuan/Nyonya %s telah check-out", room.getId(), user.getLast_name()));
            return reservationRepository.save(reservation);
        }
        return null;
    }

    // Other methods for reservation service
}