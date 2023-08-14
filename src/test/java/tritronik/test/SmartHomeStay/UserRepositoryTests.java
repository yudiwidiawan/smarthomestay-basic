package tritronik.test.SmartHomeStay;

import static org.assertj.core.api.Assertions.assertThat;
 
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import tritronik.test.SmartHomeStay.repository.UserRepository;
import tritronik.test.SmartHomeStay.entity.User;
 
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
 
    @Autowired private UserRepository repo;
     
    @Test
    public void testCreateUser() {
        // BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // String password = passwordEncoder.encode("passwordku2023");
         
        // User newUser = new User("yudiwidiawan@gmail.com", password);
        // User savedUser = repo.save(newUser);
         
        // assertThat(savedUser).isNotNull();
        // assertThat(savedUser.getId()).isGreaterThan(0);
    }
}