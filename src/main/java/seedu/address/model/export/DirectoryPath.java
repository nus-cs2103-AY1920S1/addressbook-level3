//@@author LeowWB

package seedu.address.model.export;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.nio.file.Path;

/**
 * Represents a directory path.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class DirectoryPath {

    public static final String MESSAGE_CONSTRAINTS =
            "Directory path may only consist of alphanumeric characters, spaces, and the following characters:\n"
                    + "~\\/-_!:[]()\n";

    /*
     * The following characters are allowed (in addition to alphanumeric):
     * ~\/-_!:[]()
     * Space is allowed.
     */
    public static final String VALIDATION_REGEX = "[~.\\w\\-!:\\[\\]()/\\\\ ]+";

    private final Path path;

    /**
     * Constructs a {@code DirectoryPath}.
     *
     * @param directoryPath A valid DirectoryPath.
     */
    public DirectoryPath(Path directoryPath) {
        requireNonNull(directoryPath);
        checkArgument(isValid(directoryPath.toString()), MESSAGE_CONSTRAINTS);
        path = directoryPath;
    }

    /**
     * Returns true if a given string is a valid DirectoryPath.
     */
    public static boolean isValid(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public Path getPath() {
        return path;
    }

    @Override
    public String toString() {
        return path.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DirectoryPath // instanceof handles nulls
                && path.equals(((DirectoryPath) other).path)); // state check
    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }

}
