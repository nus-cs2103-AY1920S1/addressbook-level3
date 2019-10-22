package seedu.moneygowhere.model.path;

import static java.util.Objects.requireNonNull;
import static seedu.moneygowhere.commons.util.AppUtil.checkArgument;

import seedu.moneygowhere.commons.util.FileUtil;

/**
 * Represents a file path.
 */
public class FilePath {

    public static final String MESSAGE_CONSTRAINTS =
            "File path should not contain any of the following characters: < > ? [ ] : | * .  \n"
                    + "File path also should not contain more than 218 characters and should be an existing csv file.";

    public final String fullPath;

    /**
     * Constructs a {@code Path}.
     * @param path A valid path.
     */
    public FilePath(String path) {
        requireNonNull(path);
        checkArgument(isValidPath(path), MESSAGE_CONSTRAINTS);
        fullPath = path;
    }

    /**
     * Returns true if a given string is a valid path.
     */
    public static boolean isValidPath(String test) {
        return FileUtil.isValidPath(test) && test.endsWith(".csv");
    }

    @Override
    public String toString() {
        return fullPath;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilePath // instanceof handles nulls
                && fullPath.equals(((FilePath) other).fullPath)); // state check
    }
}
