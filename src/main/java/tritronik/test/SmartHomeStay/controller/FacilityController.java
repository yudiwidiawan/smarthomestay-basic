package tritronik.test.SmartHomeStay.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tritronik.test.SmartHomeStay.api.request.FacilityRequest;
import tritronik.test.SmartHomeStay.entity.Facility;
import tritronik.test.SmartHomeStay.service.FacilityService;

@RestController
@RequestMapping("/facilities")
public class FacilityController {

    @Autowired
    private FacilityService facilityService;

    @GetMapping
    @ResponseBody
    public List<Facility> getAllFacilities() {
        return facilityService.getAllFacilities();
    }

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PostMapping("")
    public ResponseEntity<Facility> addNewRoom(@RequestBody FacilityRequest facilityRequest) throws Exception {
        return ResponseEntity.ok(facilityService.addFacility(facilityRequest));
    }

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @DeleteMapping("")
    public ResponseEntity<Object> deleteRoom(@RequestParam Long id) throws Exception {
        return facilityService.deleteFacility(id) ? ResponseEntity.ok(Map.of("message", "Facility deleted"))
                : new ResponseEntity<>(Map.of("message", "Error deleting facility"), HttpStatus.BAD_REQUEST);
    }

}
