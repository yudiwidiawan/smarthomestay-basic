package tritronik.test.SmartHomeStay.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private Float price;
    private String status;
    private Integer floorLevel;
    private Integer roomNumber;
    // Other room details, such as price, capacity, etc.

    @ManyToMany
    private List<Facility> facilities;

    public Room addFacility(Facility facility) {
        this.facilities.add(facility);
        return this;
    }
    
}