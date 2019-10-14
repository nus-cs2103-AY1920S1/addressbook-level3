package seedu.address.model.book;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's email in the address book.
 * Guarantees: immutable.
 */
public class Author {

    public static final String MESSAGE_CONSTRAINTS = "Author's name must start with an alphanumeric letter,"
            + " and it should not be blank";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum}\\p{Punct} ]*";
    public final String value;

    /**
     * Constructs an {@code Author}.
     *
     * @param author an Author's name
     */
    public Author(String author) {
        requireNonNull(author);
        checkArgument(isValidAuthor(author), MESSAGE_CONSTRAINTS);
        value = author;
    }

    public static boolean isValidAuthor(String test) {
        return test.matches(VALIDATION_REGEX);
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
