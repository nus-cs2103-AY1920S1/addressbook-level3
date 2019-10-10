package seedu.billboard.model.person;

import static java.util.Objects.requireNonNull;

public class Description {

    public static final String MESSAGE_CONSTRAINTS =
            "Description should not be blank";

    public final String description;

    public Description(String value) {
        requireNonNull(value);
        this.description = value;
    }

    public static boolean isValidDescription(String trimmedDescription) {
        return !trimmedDescription.isBlank();
    }

    public boolean isBlank() {
        return description.isBlank();
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
