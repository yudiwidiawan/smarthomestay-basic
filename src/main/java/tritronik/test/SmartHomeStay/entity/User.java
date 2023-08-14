package tritronik.test.SmartHomeStay.entity;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.SEQUENCE;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Setter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode
@Entity(name = "User")
@Getter
@Table(name = "user", uniqueConstraints = @UniqueConstraint(name = "student_email_unique", columnNames = "email"))
public class User {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "user_sequence"
    )
    @Column(name = "id", updatable = false)
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String first_name;
    @Column(name = "last_name")
    private String last_name;
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "roles", nullable = false)
    private String Roles;

    @ToString.Exclude
    @Column(name = "password", nullable = false, columnDefinition = "text")
    private String password;

    @JsonIgnore
    public String getPassword() {
        return password;
    }

}