package tritronik.test.SmartHomeStay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tritronik.test.SmartHomeStay.repository.RoomRepository;
import tritronik.test.SmartHomeStay.entity.Facility;
import tritronik.test.SmartHomeStay.entity.Room;

import java.util.List;
import java.util.Optional;

import tritronik.test.SmartHomeStay.api.request.RoomRequest;
import tritronik.test.SmartHomeStay.domain.common.query.SearchRequest;
import tritronik.test.SmartHomeStay.domain.common.query.SearchSpecification;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public Page<Room> searchRoom(SearchRequest request) {
        SearchSpecification<Room> specification = new SearchSpecification<>(request);
        Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
        return roomRepository.findAll(specification, pageable);
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public List<Room> getWithFilter() {
        return roomRepository.findAll();
    }

    public Room addRoom(RoomRequest room) {
        Room newRoom = new Room();
        newRoom.setType(room.getType());
        newRoom.setPrice(room.getPrice());
        newRoom.setFloorLevel(room.getFloorLevel());
        newRoom.setRoomNumber(room.getRoomNumber());
        newRoom.setStatus(room.getStatus());
        return roomRepository.save(newRoom);
    }

    public Room addFacility(Long id, Facility facility) {
        Optional<Room> room = roomRepository.findById(id);
        return roomRepository.save(room.get().addFacility(facility));
        
    }

    // Other methods for room service
}