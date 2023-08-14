package tritronik.test.SmartHomeStay.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reservation")
@NoArgsConstructor
@Getter
@Setter
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Room room;

    @ManyToMany
    private List<Facility> facilities;

    private String status;

    private Date startDate;

    private Date endDate;

    private Timestamp checkInTime;

    private Timestamp checkoutTime;

    @OneToOne
    private Payment payment;


}