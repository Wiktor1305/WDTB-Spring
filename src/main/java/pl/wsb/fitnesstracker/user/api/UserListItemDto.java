package pl.wsb.fitnesstracker.user.api;

public class UserListItemDto {

    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String email;

    public UserListItemDto(Long id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}