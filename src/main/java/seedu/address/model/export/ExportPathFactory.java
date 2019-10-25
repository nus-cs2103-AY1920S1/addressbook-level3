//@@author LeowWB

package seedu.address.model.export;

public class ExportPathFactory {

    public static final String MESSAGE_ILLEGAL_PATH = "The file path you have provided is not allowed.\n"
            + "File paths must consist entirely of alphanumeric characters, spaces, and the following:\n"
            + "~\\/-_!:[]()\n"
            + "It must also have one of the following extensions:\n"
            + "'.docx', '.json'";

    public static ExportPath getExportPath(String exportPath) throws IllegalArgumentException {
        if (DocumentPath.isValid(exportPath)) {
            return new DocumentPath(exportPath);
        } else if (JsonExportFilePath.isValid(exportPath)) {
            return new JsonExportPath(exportPath);
        } else {
            throw new IllegalArgumentException(MESSAGE_ILLEGAL_PATH);
        }
    }
}
