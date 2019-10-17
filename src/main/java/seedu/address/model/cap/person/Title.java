package seedu.address.model.cap.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable.
 */
public class Title {

    public final String title;

    /**
     * Constructs an {@code Address}.
     *
     * @param title A valid address.
     */
    public Title(String title) {
        requireNonNull(title);
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Title // instanceof handles nulls
                && title.equals(((Title) other).title)); // state check
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }

}
