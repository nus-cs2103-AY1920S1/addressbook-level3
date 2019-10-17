package seedu.address.model.file;

/**
 * Represents a File's FilePath in SecureIT.
 */
public class FilePath {

    public static final String MESSAGE_CONSTRAINTS = "File path should not be blank.";

    public final String value;

    /**
     * Constructs a {@code FilePath}.
     *
     * @param fileName A file's path.
     */
    public FilePath(String fileName) {
        value = fileName;
    }

    /**
     * Returns true if a given string is a valid file path.
     */
    public static boolean isValidFilePath(String test) {
        return !("".equals(test.trim()));
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilePath // instanceof handles nulls
                && value.equals(((FilePath) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
