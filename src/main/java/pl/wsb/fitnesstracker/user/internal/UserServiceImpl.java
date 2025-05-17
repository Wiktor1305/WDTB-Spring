package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserListItemDto;
import pl.wsb.fitnesstracker.user.api.UserProvider;
import pl.wsb.fitnesstracker.user.api.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;

    /**
     * Tworzy nowego użytkownika.
     *
     * @param user Obiekt użytkownika do utworzenia
     * @return Utworzony użytkownik
     * @throws IllegalArgumentException Jeśli użytkownik posiada już przypisany identyfikator
     */

    @Override
    public User createUser(final User user) {
        log.info("Creating User {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("User has already DB ID, update is not permitted!");
        }
        return userRepository.save(user);
    }


    /**
     * Pobiera użytkownika na podstawie ID.
     *
     * @param userId Identyfikator użytkownika
     * @return Obiekt optional z użytkownikiem lub pusty optional, jeśli użytkownik nie istnieje
     */

    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }


    /**
     * Pobiera użytkownika na podstawie adresu e-mail.
     *
     * @param email Adres e-mail użytkownika
     * @return Obiekt optional z użytkownikiem lub pusty optional, jeśli użytkownik nie istnieje
     */

    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Pobiera listę wszystkich użytkowników.
     *
     * @return Lista użytkowników
     */

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }


    /**
     * Wyszukuje użytkowników na podstawie przekazanych kryteriów.
     *
     * @param firstName Imię użytkownika
     * @param lastName  Nazwisko użytkownika
     * @param email     Adres e-mail użytkownika
     * @param birthdate Data urodzenia użytkownika
     * @return Lista użytkowników spełniających kryteria
     */

    public List<User> searchUsers(String firstName, String lastName, String email, LocalDate birthdate) {
        return userRepository.findAll().stream()
                .filter(user -> firstName == null || user.getFirstName().equalsIgnoreCase(firstName))
                .filter(user -> lastName == null || user.getLastName().equalsIgnoreCase(lastName))
                .filter(user -> email == null || user.getEmail().equalsIgnoreCase(email))
                .filter(user -> birthdate == null || user.getBirthdate().equals(birthdate))
                .toList();
    }

    /**
     * Usuwa użytkownika na podstawie ID.
     *
     * @param userId Identyfikator użytkownika do usunięcia
     */

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    /**
     * Aktualizuje dane użytkownika.
     *
     * @param userId      Identyfikator użytkownika do zaktualizowania
     * @param updatedUser Obiekt zawierający zaktualizowane dane użytkownika
     * @return Zaktualizowany obiekt użytkownika
     * @throws IllegalArgumentException Jeśli użytkownik o podanym ID nie istnieje
     */

    @Override
    public User updateUser(Long userId, User updatedUser) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setEmail(updatedUser.getEmail());
        user.setBirthdate(updatedUser.getBirthdate());

        return userRepository.save(user);
    }

    /**
     * Szuka użytkowników na podstawie fragmentu ich e-maila.
     * Porównanie ignoruje wielkość liter.
     * @param email Część adresu e-mail, którego szukamy
     * @return Lista użytkowników pasujących do podanego fragmentu
     */

    @Override
    public List<UserListItemDto> findUserByEmail(String email) {
        return userRepository.findAll().stream()
                .filter(user -> user.getEmail() != null && user.getEmail().toLowerCase().contains(email.toLowerCase()))
                .map(user -> new UserListItemDto(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail()
                ))
                .toList();
    }

    /**
     * Pobiera wszystkich użytkowników, którzy urodzili się przed podaną datą.
     *
     * @param birthdate Data graniczna
     * @return Lista użytkowników starszych od określonej daty
     */

    @Override
    public List<User> findUsersOlderThan(LocalDate birthdate) {
        return userRepository.findAll().stream()
                .filter(user -> user.getBirthdate().isBefore(birthdate))
                .toList();
    }

    /**
     * Pobiera listę wszystkich użytkowników w uproszczonej uformowanej wersji.
     *
     * @return Lista uproszczonych DTO użytkowników
     */

    @Override
    public List<UserListItemDto> findAllSimpleUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserListItemDto(user.getId(), user.getFirstName(), user.getLastName(), null))
                .toList();
    }

}