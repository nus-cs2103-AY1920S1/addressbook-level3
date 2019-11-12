package seedu.address.model.file;

/**
 * Represents a File's FilePath in SecureIT.
 */
public class FilePath {

    public final String value;

    /**
     * Constructs a {@code FilePath}.
     *
     * @param fileName A file's path.
     */
    public FilePath(String fileName) {
        value = fileName;
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
