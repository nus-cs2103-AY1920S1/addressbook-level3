package seedu.weme.model.path;

import static java.util.Objects.requireNonNull;

import static seedu.weme.commons.util.AppUtil.checkArgument;

import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.weme.commons.util.FileUtil;


/**
 * Wrapper class for Path for Import and Export.
 */
public class DirectoryPath {

    public static final String MESSAGE_CONSTRAINTS = "Invalid Directory Path given.";

    public final Path directoryPath;

    /**
     * Constructs an {@code DirectoryPath}.
     *
     * @param directoryPath A valid relative file path.
     */
    public DirectoryPath(String directoryPath) {
        requireNonNull(directoryPath);
        checkArgument(isValidDirectoryPath(directoryPath), MESSAGE_CONSTRAINTS);
        this.directoryPath = Paths.get(directoryPath);
    }

    /**
     * Returns true if the given string is a valid Path.
     */
    public static boolean isValidDirectoryPath(String test) {
        // Paths.get() throws InvalidPathException when the path is a invalid.
        // It is caught and becomes return false.
        return FileUtil.isValidDirectoryPath(test);
    }

    public Path toPath() {
        return directoryPath;
    }

    @Override
    public String toString() {
        return directoryPath.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DirectoryPath // instanceof handles nulls
                && directoryPath.equals(((DirectoryPath) other).directoryPath)); // state check
    }

    @Override
    public int hashCode() {
        return directoryPath.hashCode();
    }
}
