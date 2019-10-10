package seedu.billboard.model.expense;

import static java.util.Objects.requireNonNull;

/**
 * Represents an Expense's description in Billboard.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}}
 */
public class Description {

    public static final String MESSAGE_CONSTRAINTS =
            "Description has no constraints";

    public final String description;

    public Description(String value) {
        requireNonNull(value);
        this.description = value;
    }

    public static boolean isValidDescription(String trimmedDescription) {
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && description.equals(((Description) other).description)); // state check
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }
}
