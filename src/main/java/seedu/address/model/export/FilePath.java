//@@author LeowWB

package seedu.address.model.export;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Represents a file path.
 * Guarantees: immutable; is valid as declared in {@link #isValidFilePath(String)}
 */
public class FilePath {

    public static final String MESSAGE_CONSTRAINTS =
            "File path may only consist of alphanumeric characters, spaces, and the following characters:\n"
            + "~\\/-_!:[]()\n"
            + "It must also end with \".docx\".";

    /*
     * The following characters are allowed (in addition to alphanumeric):
     * ~\/-_!:[]()
     * Space is allowed.
     * FilePath is required to end with the String: ".docx"
     */
    public static final String VALIDATION_REGEX = "[~\\w\\-!:\\[\\]()/\\\\ ]+\\.docx";

    private final Path path;

    /**
     * Constructs a {@code FilePath}.
     *
     * @param filePath A valid document file path.
     */
    public FilePath(String filePath) {
        requireNonNull(filePath);
        checkArgument(isValidFilePath(filePath), MESSAGE_CONSTRAINTS);
        path = Paths.get(filePath);
    }

    /**
     * Returns true if a given string is a valid document file path.
     */
    public static boolean isValidFilePath(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return path.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilePath // instanceof handles nulls
                && path.equals(((FilePath) other).path)); // state check
    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }

}
