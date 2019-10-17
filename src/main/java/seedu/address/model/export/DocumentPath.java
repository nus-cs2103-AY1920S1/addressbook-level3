//@@author LeowWB

package seedu.address.model.export;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Represents the full path to a document, including parent directories.
 * Guarantees: immutable; is valid as declared in {@link #isValidDocumentPath(String)}
 */
public class DocumentPath {

    public static final String MESSAGE_CONSTRAINTS =
            "Document path may only consist of alphanumeric characters, spaces, and the following characters:\n"
            + "~\\/-_!:[]()\n"
            + "It must also end with \".docx\".";

    /*
     * The following characters are allowed (in addition to alphanumeric):
     * ~\/-_!:[]()
     * Space is allowed.
     * DocumentPath is required to end with the String: ".docx"
     */
    public static final String VALIDATION_REGEX = "[~\\w\\-!:\\[\\]()/\\\\ ]+\\.docx";

    private final Path path;

    /**
     * Constructs a {@code DocumentPath}.
     *
     * @param documentPath A valid document path.
     */
    public DocumentPath(String documentPath) {
        requireNonNull(documentPath);
        checkArgument(isValidDocumentPath(documentPath), MESSAGE_CONSTRAINTS);
        path = Paths.get(documentPath);
    }

    /**
     * Returns true if a given string is a valid document path.
     */
    public static boolean isValidDocumentPath(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return path.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DocumentPath // instanceof handles nulls
                && path.equals(((DocumentPath) other).path)); // state check
    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }

}
