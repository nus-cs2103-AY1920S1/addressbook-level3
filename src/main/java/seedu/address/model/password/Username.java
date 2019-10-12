package seedu.address.model.password;

import static java.util.Objects.requireNonNull;

public class Username {
    public final String value;

    public Username(String username) {
        requireNonNull(username);
        //checkArgument(isValidPasswordValue(passwordValue), MESSAGE_CONSTRAINTS);
        value = username;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Username // instanceof handles nulls
                && value.equals(((Username) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
