package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Username} and {@code Password} matches the entered value.
 */
public class LoginCredentialsPredicate implements Predicate<Person> {
    private final Username username;
    private final Password password;

    public LoginCredentialsPredicate(Username username, Password password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean test(Person person) {
        return person.getUsername().equals(username) && person.getPassword().equals(password);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginCredentialsPredicate // instanceof handles nulls
                && username.equals(((LoginCredentialsPredicate) other).username)
                && password.equals(((LoginCredentialsPredicate) other).password)); // state check
    }

}
