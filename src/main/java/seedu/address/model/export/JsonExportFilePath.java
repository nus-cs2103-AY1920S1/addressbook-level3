//@@author LeowWB

package seedu.address.model.export;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Represents the path to a JSON export file from its immediate parent directory.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class JsonExportFilePath {

    public static final String MESSAGE_CONSTRAINTS =
            "JSON export file path may only consist of alphanumeric characters, spaces, and the following characters:\n"
                    + "-_![]()\n"
                    + "It must also end with \".json\".";

    public static final String VALIDATION_REGEX = "[\\w\\-!\\[\\]() ]+\\.[Jj][Ss][Oo][Nn]";

    private final Path path;

    /**
     * Constructs a {@code JsonExportFilePath}.
     *
     * @param jsonExportFilePath A valid JSON export file path.
     */
    public JsonExportFilePath(String jsonExportFilePath) {
        requireNonNull(jsonExportFilePath);
        checkArgument(isValid(jsonExportFilePath), MESSAGE_CONSTRAINTS);
        path = Paths.get(jsonExportFilePath);
    }

    /**
     * Returns true if a given string is a valid jsonExport file path.
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
                || (other instanceof JsonExportFilePath // instanceof handles nulls
                && path.equals(((JsonExportFilePath) other).path)); // state check
    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }

}
