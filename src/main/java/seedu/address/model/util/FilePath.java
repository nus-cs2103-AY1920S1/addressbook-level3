package seedu.address.model.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a FlashCard's Question in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidFilePath(String)}
 */
public class FilePath {

    public static final String MESSAGE_CONSTRAINTS =
            "File path can take any value, as long as it is a valid file path.";

    /*
     * The first character of the question must not be a whitespace
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String fullFilePath;

    /**
     * Constructs a {@code Question}.
     *
     * @param filePath A valid question.
     */
    public FilePath(String filePath) {
        requireNonNull(filePath);
        checkArgument(isValidFilePath(filePath), MESSAGE_CONSTRAINTS);
        fullFilePath = filePath;
    }

    /**
     * Returns true if a given string is a valid question.
     */
    public static boolean isValidFilePath(String test) {
        return true;
        //TODO test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullFilePath;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilePath // instanceof handles nulls
                && fullFilePath.equals(((FilePath) other).fullFilePath)); // state check
    }

    @Override
    public int hashCode() {
        return fullFilePath.hashCode();
    }

}
