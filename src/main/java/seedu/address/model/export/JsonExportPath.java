//@@author LeowWB

package seedu.address.model.export;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.flashcard.FlashCard;

/**
 * Represents the full path to a JSON export file, including parent directories.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class JsonExportPath extends ExportPath {

    public static final String MESSAGE_CONSTRAINTS =
            "JSON export file path may only consist of alphanumeric characters, spaces, and the following characters:\n"
            + "~\\/-_!:[]()\n"
            + "It must also end with \".json\".";

    /*
     * The following characters are allowed (in addition to alphanumeric):
     * ~\/-_!:[]()
     * Space is allowed.
     * Required to end with the String: ".json"
     */
    public static final String VALIDATION_REGEX = "[.~\\w\\-!:\\[\\]()/\\\\ ]+\\.[Jj][Ss][Oo][Nn]";

    private final DirectoryPath directoryPath;
    private final JsonExportFilePath jsonExportFilePath;

    /**
     * Constructs a {@code JsonExportPath}.
     *
     * @param jsonExportPath A valid JSON export path.
     */
    public JsonExportPath(String jsonExportPath) {
        requireNonNull(jsonExportPath);
        checkArgument(isValid(jsonExportPath), MESSAGE_CONSTRAINTS);
        this.directoryPath = extractDirectoryPath(jsonExportPath);
        this.jsonExportFilePath = extractJsonExportFilePath(jsonExportPath);
    }

    /**
     * Returns true if a given string is a valid json export path.
     */
    public static boolean isValid(String test) {
        requireNonNull(test);
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Helper method to get the JSON export file path from a given String.
     *
     * @param jsonExportPathString String representing the full path of a JSON export file
     * @return JsonExportFilePath representing the path of the JSON export file,
     * relative to its immediate parent directory
     */
    private static JsonExportFilePath extractJsonExportFilePath(String jsonExportPathString) {
        requireNonNull(jsonExportPathString);

        return new JsonExportFilePath(
                ExportPath.extractFilePathNoDirectoryString(jsonExportPathString)
        );
    }

    @Override
    public Path getPath() {
        Path dirPath = directoryPath.getPath();
        Path jsonFilePath = jsonExportFilePath.getPath();

        return dirPath.resolve(jsonFilePath);
    }

    @Override
    public String toString() {
        return directoryPath.toString() + File.separator + jsonExportFilePath.toString();
    }

    @Override
    public void export(List<FlashCard> list) throws IOException {
        requireNonNull(list);

        try {
            directoryPath.createIfNotPresent();
            JsonExportUtil.exportFlashCardsToJson(list, this);
        } catch (IOException e) {
            throw e;
        }
    }

    @Override
    public Optional<List<FlashCard>> importFrom() throws DataConversionException {
        return JsonImportUtil.importFlashCardsFromJson(
                this
        );
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof JsonExportPath // instanceof handles nulls
                && directoryPath.equals(((JsonExportPath) other).directoryPath)
                && jsonExportFilePath.equals(((JsonExportPath) other).jsonExportFilePath)); // state check
    }

    @Override
    public int hashCode() {
        return directoryPath.hashCode() + jsonExportFilePath.hashCode();
    }

}
