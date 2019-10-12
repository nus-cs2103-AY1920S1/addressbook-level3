package seedu.address.model.password;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Password's description in the password book.
 */
public class Description {
    public final String value;

    public Description(String description) {
        requireNonNull(description);
        //checkArgument(isValidPasswordValue(passwordValue), MESSAGE_CONSTRAINTS);
        value = description;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && value.equals(((Description) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
