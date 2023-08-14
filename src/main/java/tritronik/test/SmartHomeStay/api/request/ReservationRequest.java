package tritronik.test.SmartHomeStay.api.request;

import java.sql.Date;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tritronik.test.SmartHomeStay.entity.Facility;

@Getter
@Setter
@NoArgsConstructor
public class ReservationRequest {
    private Long roomId;
    private Date startDate;
    private Date endDate;
    private List<Facility> facilities;
}
