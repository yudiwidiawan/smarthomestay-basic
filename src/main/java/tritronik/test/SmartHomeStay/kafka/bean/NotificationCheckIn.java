package tritronik.test.SmartHomeStay.kafka.bean;

import java.sql.Timestamp;

public class NotificationCheckIn {
    private String roomId;
    private String guestName;
    private Timestamp checkInTime;

    public NotificationCheckIn(String roomId, String guestName, Timestamp checkInTime) {
        this.roomId = roomId;
        this.guestName = guestName;
        this.checkInTime = checkInTime;
    }

    @Override
    public String toString() {
        return "NotificationCheckIn{" +
                "roomId='" + roomId + '\'' +
                ", guestName='" + guestName + '\'' +
                ", checkInTime=" + checkInTime +
                '}';
    }
}
