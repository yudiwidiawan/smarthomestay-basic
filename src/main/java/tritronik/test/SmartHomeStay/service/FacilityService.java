package tritronik.test.SmartHomeStay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tritronik.test.SmartHomeStay.api.request.FacilityRequest;
import tritronik.test.SmartHomeStay.entity.Facility;
import tritronik.test.SmartHomeStay.repository.FacilityRepository;

@Service
public class FacilityService {
    @Autowired
    FacilityRepository facilityRepository;

    public Facility getFacility(Long id) {
        return facilityRepository.findById(id).get();
    }

    public List<Facility> getAllFacilities() {
        return facilityRepository.findAll();
    }
    
    public Facility addFacility(FacilityRequest facilityRequest) {
        Facility facility = new Facility();
        facility.setName(facilityRequest.getName());
        return facilityRepository.save(facility);
    }

    public Boolean deleteFacility(Long id) {
        try {
            facilityRepository.deleteById(id);
            return true;
        } catch(Exception ex) {
            return false;
        }
    }
}
