package seedu.algobase.model.problem;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.util.AppUtil.checkArgument;

/**
 * Represents a Problem's author number in the algobase.
 * Guarantees: immutable; is valid as declared in {@link #isValidAuthor(String)}
 */
public class Author {


    public static final String MESSAGE_CONSTRAINTS =
            "Author numbers should only contain numbers, and it should be at least 3 digits long";
    public static final Author DEFAULT_AUTHOR = new Author();

    /*
     * The first character of the author's name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public final String value;

    /**
     * Constructs a {@code Author}.
     *
     * @param author A valid author number.
     */
    public Author(String author) {
        requireNonNull(author);
        checkArgument(isValidAuthor(author), MESSAGE_CONSTRAINTS);
        value = author;
    }

    private Author() {
        value = "";
    }

    /**
     * Returns true if a given string is a valid author number.
     */
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
