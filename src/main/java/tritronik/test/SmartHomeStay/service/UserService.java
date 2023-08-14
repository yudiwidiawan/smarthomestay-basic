package tritronik.test.SmartHomeStay.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tritronik.test.SmartHomeStay.repository.UserRepository;
import tritronik.test.SmartHomeStay.entity.User;
import java.util.List;
import tritronik.test.SmartHomeStay.api.request.UserRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;


    public List<User> GetAllUsers() {
        return userRepository.findAll();
    }

    public User AddUser(UserRequest user) {
        User newUser = new User();
        newUser.setFirst_name(user.getFirst_name());
        newUser.setLast_name(user.getLast_name());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        newUser.setRoles(user.getRoles());
        return userRepository.save(newUser);
    }

    // Other methods for user service
}