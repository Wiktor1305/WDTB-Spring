package pl.wsb.fitnesstracker.user.internal;

import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserListItemDto;

@Component
class UserMapper {


    /**
     * Konwertuje obiekt użytkownika na pełny DTO.
     *
     * @param user Obiekt użytkownika
     * @return Pełny obiekt DTO
     */

    UserDto toDto(User user) {
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail());
    }


    /**
     * Konwertuje pełny DTO na obiekt użytkownika.
     *
     * @param userDto Pełny DTO użytkownika
     * @return Obiekt użytkownika
     */

    User toEntity(UserDto userDto) {
        return new User(
                userDto.firstName(),
                userDto.lastName(),
                userDto.birthdate(),
                userDto.email());
    }

    /**
     * Tworzy uproszczony DTO z podstawowymi danymi użytkownika (ID, imię, nazwisko i e-mail).
     *
     * @param user Obiekt użytkownika
     * @return Uproszczony obiekt DTO
     */

    UserListItemDto toEmailListItemDto(User user) {
        return new UserListItemDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }

    /**
     * Tworzy uproszczony DTO użytkownika z danymi ID, imię, nazwisko (bez e-maila).
     *
     * @param user Obiekt użytkownika
     * @return Uproszczony obiekt DTO
     */

    UserListItemDto toListItemDto(User user) {
        return new UserListItemDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                null
        );
    }



}
