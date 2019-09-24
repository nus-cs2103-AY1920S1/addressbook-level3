package seedu.address.model.book;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's email in the address book.
 * Guarantees: immutable.
 */
public class Author {

    public static final String MESSAGE_CONSTRAINTS = "Please exclude special characters.";
    public final String value;

    /**
     * Constructs an {@code Author}.
     *
     * @param author A valid email address.
     */
    public Author(String author) {
        requireNonNull(author);
        value = author;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Author // instanceof handles nulls
                && value.equals(((Author) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
