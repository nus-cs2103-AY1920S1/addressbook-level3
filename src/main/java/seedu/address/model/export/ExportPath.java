//@@author LeowWB

package seedu.address.model.export;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.flashcard.FlashCard;

/**
 * Abstract class from which all export paths inherit. A subclass of this would store the path to
 * an export file.
 *
 * Please note: the word "export" is used in the capacity of a noun, and not a verb. This is not "the
 * path that we export to", but rather "the path of one of our exports". As such, this same class is
 * used by both the {@code export} and {@code import} commands.
 */
public abstract class ExportPath {
    public abstract Path getPath();
    public abstract void export(List<FlashCard> list) throws IOException;
    public abstract Optional<List<FlashCard>> importFrom()
            throws DataConversionException, UnsupportedOperationException;

    /**
     * Helper method to get the directory path from a given String.
     *
     * @param exportPathString String representing the full path of a document
     * @return DirectoryPath representing the path of the most nested directory within the given String
     */
    static DirectoryPath extractDirectoryPath(String exportPathString) {
        requireNonNull(exportPathString);

        return new DirectoryPath(
                Paths.get(
                        exportPathString
                )
                .getParent()
        );
    }

    /**
     * Converts the ExportPath to a String representing the absolute path. Note that this is not the same as calling
     * toString().
     *
     * @return String representing the absolute path of this ExportPath
     */
    public String toAbsolutePathString() {
        return this
                .getPath()
                .toAbsolutePath()
                .normalize()
                .toString();
    }

    /**
     * Given a String representing a file path, extract the portion of the String that corresponds to the path
     * to the file from its immediate parent directory.
     * e.g. {@code extractFilePathNoDirectoryString("folder/directory/file.ext")} will return {@code "file.ext"}.
     *
     * @param fullPathString String representing the full file path
     * @return String representing path to the file from its immediate parent directory
     */
    static String extractFilePathNoDirectoryString(String fullPathString) {
        requireNonNull(fullPathString);

        Path fullPath = Paths.get(fullPathString);
        int nameCount = fullPath.getNameCount();

        return fullPath
                .subpath(nameCount - 1, nameCount)
                .toString();
    }
}
