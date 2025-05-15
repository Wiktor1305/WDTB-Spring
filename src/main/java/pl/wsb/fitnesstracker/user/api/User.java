package pl.wsb.fitnesstracker.user.api;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Nullable
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @Column(nullable = false, unique = true)
    private String email;

    /**
     * tworzy nowego usera
     * @param firstName imie usera
     * @param lastName nazwisko usera
     * @param birthdate data urodzenia usera
     * @param email email usera
     */

    public User(
            final String firstName,
            final String lastName,
            final LocalDate birthdate,
            final String email) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.email = email;
    }

    /**
     * ustawia imie usera
     * @param firstName nowe imie
     */

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * ustawia nazwisko usera
     * @param lastName nowe nazwisko
     */

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * ustawia date urodzenia usera
     * @param birthdate nowa data urodzenia
     */

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    /**
     * ustawia email usera
     * @param email nowy email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}

