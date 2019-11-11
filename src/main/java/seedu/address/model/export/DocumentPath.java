//@@author LeowWB

package seedu.address.model.export;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import seedu.address.model.flashcard.FlashCard;

/**
 * Represents the full path to a document, including parent directories.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class DocumentPath extends ExportPath {

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
        checkArgument(isValid(documentPath), MESSAGE_CONSTRAINTS);
        this.directoryPath = extractDirectoryPath(documentPath);
        this.documentFilePath = extractDocumentFilePath(documentPath);
    }

    /**
     * Returns true if a given string is a valid document path.
     */
    public static boolean isValid(String test) {
        requireNonNull(test);
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Helper method to get the document file path from a given String.
     *
     * @param documentFilePathString String representing the full path of a document
     * @return DocumentFilePath representing the path of the document, relative to its immediate parent directory
     */
    private static DocumentFilePath extractDocumentFilePath(String documentFilePathString) {
        requireNonNull(documentFilePathString);

        return new DocumentFilePath(
                ExportPath.extractFilePathNoDirectoryString(
                        documentFilePathString
                )
        );
    }

    @Override
    public Path getPath() {
        Path dirPath = directoryPath.getPath();
        Path docFilePath = documentFilePath.getPath();

        return dirPath.resolve(docFilePath);
    }

    @Override
    public String toString() {
        return directoryPath.toString() + File.separator + documentFilePath.toString();
    }

    @Override
    public void export(List<FlashCard> list) throws IOException {
        try {
            directoryPath.createIfNotPresent();
            DocumentExportUtil.exportFlashCardsToDocument(list, this);
        } catch (IOException e) {
            throw e;
        }
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

    @Override
    public Optional<List<FlashCard>> importFrom() throws UnsupportedOperationException {
        throw new UnsupportedOperationException(
                "Importing from document file is not supported."
        );
    }
}
