//@@author LeowWB

package seedu.address.model.export;

import static java.util.Objects.requireNonNull;

/**
 * Class used for creating of {@code ExportPath}s. Follows the factory pattern.
 */
public class ExportPathFactory {

    public static final String MESSAGE_ILLEGAL_PATH = "The file path you have provided is not allowed.\n"
            + "File paths must consist entirely of alphanumeric characters, spaces, and the following:\n"
            + "~\\/-_!:[]()\n"
            + "It must also have one of the following extensions:\n"
            + "'.docx', '.json'";

    /**
     * Creates an {@code ExportPath} from a given String.
     *
     * @param exportPath String representing the path of the file that we plan to export to
     * @return ExportPath that represents the same path as the given String
     * @throws IllegalArgumentException if the given String does not match the format of any of the ExportPaths
     */
    public static ExportPath getExportPath(String exportPath) throws IllegalArgumentException {
        requireNonNull(exportPath);

        if (DocumentPath.isValid(exportPath)) {
            return new DocumentPath(exportPath);
        } else if (JsonExportPath.isValid(exportPath)) {
            return new JsonExportPath(exportPath);
        } else {
            throw new IllegalArgumentException(MESSAGE_ILLEGAL_PATH);
        }
    }
}
