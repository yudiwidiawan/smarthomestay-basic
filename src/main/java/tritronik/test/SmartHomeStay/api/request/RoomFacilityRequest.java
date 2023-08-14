package tritronik.test.SmartHomeStay.api.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoomFacilityRequest {
    private Long roomId;
    private Long facilityId;
}
