package seedu.mark.model.bookmark;

import static java.util.Objects.requireNonNull;
import static seedu.mark.commons.util.AppUtil.checkArgument;

/**
 * Represents a Folder in Mark.
 * Guarantees: immutable; name is valid as declared in {@link #isValidFolder(String)}
 */
public class Folder {
    public static final String MESSAGE_CONSTRAINTS = "Folder names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";
    public static final String DEFAULT_FOLDER_NAME = "ROOT";
    public static final Folder ROOT_FOLDER = new Folder(DEFAULT_FOLDER_NAME);

    public final String folderName;

    /**
     * Constructs a {@code Folder}.
     *
     * @param folderName A valid folder name.
     */
    public Folder(String folderName) {
        requireNonNull(folderName);
        checkArgument(isValidFolder(folderName), MESSAGE_CONSTRAINTS);
        this.folderName = folderName;
    }

    /**
     * Returns true if a given string is a valid folder name.
     */
    public static boolean isValidFolder(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Folder // instanceof handles nulls
            && folderName.equals(((Folder) other).folderName)); // state check
    }

    @Override
    public int hashCode() {
        return folderName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return folderName;
    }

}
