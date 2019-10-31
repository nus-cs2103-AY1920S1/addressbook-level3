package seedu.moneygowhere.model.path;

import static java.util.Objects.requireNonNull;
import static seedu.moneygowhere.commons.util.AppUtil.checkArgument;

import java.io.File;

import seedu.moneygowhere.commons.util.FileUtil;

/**
 * Represents a folder path.
 */
public class FolderPath {
    public static final String MESSAGE_CONSTRAINTS =
            "Folder path should exist and not contain any of the following characters: < > ? [ ] : | * .  \n"
                    + "Folder path also should not contain more than 218 characters.";

    public final String fullPath;

    /**
     * Constructs a {@code Path}.
     * @param path A valid folder path.
     */
    public FolderPath(String path) {
        requireNonNull(path);
        checkArgument(isValidFolderPath(path), MESSAGE_CONSTRAINTS);
        fullPath = path;
    }

    /**
     * Returns true if a given string is a valid folder path.
     */
    public static boolean isValidFolderPath(String test) {
        return FileUtil.isValidPath(test) && new File(test).isDirectory();
    }

    @Override
    public String toString() {
        return fullPath;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FolderPath // instanceof handles nulls
                && fullPath.equals(((FolderPath) other).fullPath)); // state check
    }
}
