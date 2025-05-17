package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserListItemDto;
import pl.wsb.fitnesstracker.user.api.UserNotFoundException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    /**
     * Pobiera listę wszystkich użytkowników.
     *
     * @return Lista obiektów DTO reprezentujących użytkowników
     */

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    /**
     * Pobiera uproszczoną listę użytkowników z podstawowymi danymi (ID, imię, nazwisko).
     *
     * @return Lista uproszczonych DTO użytkowników
     */

    @GetMapping("/basic")
    public List<UserListItemDto> getAllUsersBasic() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toListItemDto)
                .toList();
    }

    /**
     * Pobiera dane pojedynczego użytkownika na podstawie identyfikatora.
     *
     * @param id ID użytkownika do znalezienia
     * @return Obiekt DTO użytkownika
     * @throws UserNotFoundException jeśli użytkownik o podanym ID nie istnieje
     */

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUser(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException( id));
    }

    /**
     * wyszukuje userow po zadanych kryteriach
     * @param firstName imie
     * @param lastName nazwisko
     * @param email email
     * @param birthdate data urodzenia
     * @return lista znalezionych userow
     */

    @GetMapping("/search")
    public List<UserDto> searchUsers(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthdate
    ) {
        return userService.searchUsers(firstName, lastName, email, birthdate)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    /**
     * tworzy nowego usera
     * @param userDto dane usera
     * @return utworzony user
     */

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody UserDto userDto) {
        User createdUser = userService.createUser(userMapper.toEntity(userDto));
        return userMapper.toDto(createdUser);
    }

    /**
     * usuwa usera o podanym ID
     * @param id ID usera
     */

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    /**
     * aktualizuje dane usera
     * @param id ID usera
     * @param userDto nowe dane usera
     * @return zaktualizowany user
     */

    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        User updatedUser = userService.updateUser(id, userMapper.toEntity(userDto));
        return userMapper.toDto(updatedUser);
    }

    /**
     * Wyszukuje użytkowników na podstawie fragmentu adresu e-mail.
     *
     * @param email Fragment adresu e-mail do wyszukania
     * @return Lista uproszczonych DTO użytkowników pasujących do podanego fragmentu
     */

    @GetMapping("/email")
    public List<UserListItemDto> getUserByEmail(@RequestParam String email) {
        return userService.findUserByEmail(email);
    }

    /**
     * Pobraj użytkowników starszych niż podany czas.
     *
     * @param time Data graniczna
     * @return Lista użytkowników starszych niż data graniczna
     */

    @GetMapping("/older/{time}")
    public List<UserDto> getUsersOlderThan(@PathVariable("time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate time) {
        return userService.findUsersOlderThan(time)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    /**
     * Pobiera podstawową listę wszystkich użytkowników w uproszczonej formie.
     *
     * @return Odpowiedź HTTP z listą uproszczonych użytkowników
     */

    @GetMapping("/simple")
    public ResponseEntity<List<UserListItemDto>> getAllSimpleUsers() {
        List<UserListItemDto> simpleUsers = userService.findAllSimpleUsers();
        return ResponseEntity.ok(simpleUsers);
    }


}