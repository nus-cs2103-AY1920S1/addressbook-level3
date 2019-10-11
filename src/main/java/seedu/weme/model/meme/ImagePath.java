package seedu.weme.model.meme;

import static java.util.Objects.requireNonNull;
import static seedu.weme.commons.util.AppUtil.checkArgument;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.weme.commons.util.FileUtil;

/**
 * Wrapper class for Path in {@code Meme}.
 * Acts as the identity reference to a {@code Meme}.
 */
public class ImagePath {

    public static final String MESSAGE_CONSTRAINTS = "File not found or invalid file path given";

    public final Path filePath;

    /**
     * Constructs an {@code ImagePath}.
     *
     * @param relativeFilePath A valid relative file path.
     */
    public ImagePath(String relativeFilePath) {
        requireNonNull(relativeFilePath);
        checkArgument(isValidFilePath(relativeFilePath), MESSAGE_CONSTRAINTS);
        this.filePath = Paths.get(relativeFilePath);
    }

    /**
     * Returns true if the given string is a valid Path.
     */
    public static boolean isValidFilePath(String test) {
        // Paths.get() throws InvalidPathException when the path is a invalid.
        // It is caught and becomes return false.
        try {
            return FileUtil.isValidPath(test)
                    && FileUtil.isFileExists(Paths.get(test));
        } catch (InvalidPathException e) {
            return false;
        }
    }

    public Path getFilePath() {
        return filePath;
    }

    @Override
    public String toString() {
        return filePath.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImagePath // instanceof handles nulls
                && toString().equals(((ImagePath) other).toString())); // state check
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
