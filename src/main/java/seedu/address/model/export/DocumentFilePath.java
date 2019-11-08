//@@author LeowWB

package seedu.address.model.export;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Represents the path to a document from its immediate parent directory.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class DocumentFilePath {

    public static final String MESSAGE_CONSTRAINTS =
            "Document file path may only consist of alphanumeric characters, spaces, and the following characters:\n"
                    + "-_![]()\n"
                    + "It must also end with \".docx\".";

    public static final String VALIDATION_REGEX = "[\\w\\-!\\[\\]() ]+\\.[Dd][Oo][Cc][Xx]";

    private final Path path;

    /**
     * Constructs a {@code DocumentFilePath}.
     *
     * @param documentFilePath A valid document file path.
     */
    public DocumentFilePath(String documentFilePath) {
        requireNonNull(documentFilePath);
        checkArgument(isValid(documentFilePath), MESSAGE_CONSTRAINTS);
        path = Paths.get(documentFilePath);
    }

    /**
     * Returns true if a given string is a valid document file path.
     */
    public static boolean isValid(String test) {
        requireNonNull(test);
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
                || (other instanceof DocumentFilePath // instanceof handles nulls
                && path.equals(((DocumentFilePath) other).path)); // state check
    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }

}
