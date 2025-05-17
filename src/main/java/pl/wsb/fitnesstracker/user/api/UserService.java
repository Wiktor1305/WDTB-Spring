package pl.wsb.fitnesstracker.user.api;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface (API) for modifying operations on {@link User} entities through the API.
 * Implementing classes are responsible for executing changes within a database transaction, whether by continuing an existing transaction or creating a new one if required.
 */
public interface UserService {

    /**
     * tworzy nowego usera
     * @param user user do utworzenia
     * @return utworzony user
     */

    User createUser(User user);

    /**
     * wyszukuje userow po zadanych kryteriach
     * @param firstName imie usera
     * @param lastName nazwisko usera
     * @param email email usera
     * @param birthdate data urodzenia usera
     * @return lista userow spelniajacych kryteria
     */

    List<User> searchUsers(String firstName, String lastName, String email, LocalDate birthdate);

    /**
     * usuwa usera o konkretnym ID
     * @param userId ID usera
     */

    void deleteUser(Long userId);

    /**
     * aktualizuje dane usera
     * @param userId ID usera
     * @param user dane do aktualizacji
     * @return zaktualizowany user
     */

    User updateUser(Long userId, User user);

    /**
     * Znajduje użytkownika na podstawie adresu e-mail (bez rozróżniania wielkości liter).
     * @param email adres e-mail użytkownika
     * @return lista zawierająca tylko jeden element: id i email użytkownika
     */
    List<UserListItemDto> findUserByEmail(String email);

    /**
     * Pobiera listę użytkowników starszych niż podana data.
     *
     * @param birthdate Data graniczna (użytkownicy muszą być starsi od tej daty)
     * @return Lista użytkowników spełniających kryterium wieku
     */

    List<User> findUsersOlderThan(LocalDate birthdate);

    /**
     * Pobiera listę uproszczoną wszystkich użytkowników (bez szczegółowych danych).
     * Wynikowa lista zawiera jedynie ID, imię i nazwisko użytkownika.
     *
     * @return Lista uproszczonych DTO użytkowników
     */

    List<UserListItemDto> findAllSimpleUsers();

}
