package tritronik.test.SmartHomeStay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tritronik.test.SmartHomeStay.service.FacilityService;
import tritronik.test.SmartHomeStay.service.RoomService;
import tritronik.test.SmartHomeStay.api.request.RoomFacilityRequest;
import tritronik.test.SmartHomeStay.api.request.RoomRequest;
import tritronik.test.SmartHomeStay.domain.common.query.SearchRequest;
import tritronik.test.SmartHomeStay.entity.Facility;
import tritronik.test.SmartHomeStay.entity.Room;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    
    @Autowired
    private RoomService roomService;
    @Autowired
    private FacilityService facilityService;

    @PostMapping(value = "/search")
    public Page<Room> search(@RequestBody SearchRequest request) {
        return roomService.searchRoom(request);
    }

    @GetMapping
    @ResponseBody
    public List<Room> getAllRooms() {
            
        return roomService.getAllRooms();
    }

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PostMapping("")
    public ResponseEntity<Room> addNewRoom(@RequestBody RoomRequest room) throws Exception {
        return ResponseEntity.ok(roomService.addRoom(room));
    }

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PostMapping("/add-facility")
    public ResponseEntity<Room> addFacility(@RequestBody RoomFacilityRequest rmFacility) throws Exception {
        Facility facility = facilityService.getFacility(rmFacility.getFacilityId());
        return ResponseEntity.ok(roomService.addFacility(rmFacility.getRoomId(), facility));
    }



    // Other room-related endpoints
}