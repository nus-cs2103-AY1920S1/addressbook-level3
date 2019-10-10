package seedu.address.model.file;

/**
 * Represents a File's FileName in SecureIT.
 */
public class FileName {

    public final String value;

    /**
     * Constructs a {@code FileName}.
     *
     * @param fileName A file's name.
     */
    public FileName(String fileName) {
        value = fileName;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FileName // instanceof handles nulls
                && value.equals(((FileName) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
