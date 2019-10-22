//@@author LeowWB

package seedu.address.model.export;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.File;
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
    public static final String VALIDATION_REGEX = "[.~\\w\\-!:\\[\\]()/\\\\ ]+\\.[Dd][Oo][Cc][Xx]";

    private final DirectoryPath directoryPath;
    private final DocumentFilePath documentFilePath;

    /**
     * Constructs a {@code DocumentPath}.
     *
     * @param documentPath A valid document path.
     */
    public DocumentPath(String documentPath) {
        requireNonNull(documentPath);
        checkArgument(isValidDocumentPath(documentPath), MESSAGE_CONSTRAINTS);
        this.directoryPath = extractDirectoryPath(documentPath);
        this.documentFilePath = extractDocumentFilePath(documentPath);
    }

    /**
     * Returns true if a given string is a valid document path.
     */
    public static boolean isValidDocumentPath(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    private static DirectoryPath extractDirectoryPath(String documentPathString) {
        Path fullPath = Paths.get(documentPathString);
        int nameCount = fullPath.getNameCount();

        if (nameCount == 1) {
            return new DirectoryPath("./");
        }

        return new DirectoryPath(
                fullPath
                .subpath(0, nameCount - 1)
                .toString()
        );
    }

    private static DocumentFilePath extractDocumentFilePath(String documentFilePathString) {
        Path fullPath = Paths.get(documentFilePathString);
        int nameCount = fullPath.getNameCount();

        return new DocumentFilePath(
                fullPath
                .subpath(nameCount - 1, nameCount)
                .toString()
        );
    }

    @Override
    public String toString() {
        return directoryPath.toString() + File.separator + documentFilePath.toString();
    }

    public String toAbsolutePathString() {
        Path dirPath = Paths.get(directoryPath.toString());
        Path docFilePath = Paths.get(documentFilePath.toString());

        return dirPath
                .resolve(docFilePath)
                .toAbsolutePath()
                .normalize()
                .toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DocumentPath // instanceof handles nulls
                && directoryPath.equals(((DocumentPath) other).directoryPath)
                && documentFilePath.equals(((DocumentPath) other).documentFilePath)); // state check
    }

    @Override
    public int hashCode() {
        return directoryPath.hashCode() + documentFilePath.hashCode();
    }

}
