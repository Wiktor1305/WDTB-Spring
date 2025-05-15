package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.user.api.User;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping("/basic")
    public List<UserListItemDto> getAllUsersBasic() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toListItemDto)
                .toList();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUser(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
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

}