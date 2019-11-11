package seedu.address.model.file;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a File's FileName in SecureIT.
 */
public class FileName {

    public static final String MESSAGE_CONSTRAINTS = "File name should not be empty";

    public final String value;

    /**
     * Constructs a {@code FileName}.
     *
     * @param fileName A file's name.
     */
    public FileName(String fileName) {
        requireNonNull(fileName);
        checkArgument(isValidFileName(fileName));
        value = fileName;
    }

    /**
     * Constructs a {@code FileName} using file name and extension.
     *
     * @param fileName A file's name.
     * @param extension A file's extension.
     */
    public static FileName constructWithExtension(String fileName, String extension) {
        requireAllNonNull(fileName, extension);
        if (extension.trim().equals("")) {
            return new FileName(fileName);
        }
        return new FileName(fileName + "." + extension);
    }

    /**
     * Returns true if a given string is a valid file name.
     */
    public static boolean isValidFileName(String test) {
        return !("".equals(test)); // trim not required since empty space can be valid file name
    }

    /**
     * Gets the part of the file name without extension.
     */
    public String getFileNameWithoutExtention() {
        if (value.lastIndexOf('.') == -1) {
            return value;
        }
        return value.substring(0, value.lastIndexOf('.'));
    }

    /**
     * Gets the file extension of the file name.
     */
    public String getExtension() {
        if (value.lastIndexOf('.') == -1) {
            return "";
        }
        return value.substring(value.lastIndexOf('.') + 1);
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
