package tritronik.test.SmartHomeStay.api.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoomRequest {
    private String type;
    private Float price;
    private String status;
    private Integer floorLevel;
    private Integer roomNumber;
}