//@@author LeowWB

package seedu.address.model.export;

import java.io.IOException;
import java.nio.file.Files;
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
    public abstract String toAbsolutePathString();
    public abstract void export(List<FlashCard> list) throws IOException;
    public abstract Optional<List<FlashCard>> importFrom() throws DataConversionException, UnsupportedOperationException;

    void createDirectoryIfNotPresent(DirectoryPath directoryPath) throws IOException {
        try {
            Files.createDirectories(
                    directoryPath.getPath()
            );
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * Helper method to get the directory path from a given String.
     *
     * @param exportPathString String representing the full path of a document
     * @return DirectoryPath representing the path of the most nested directory within the given String
     */
    static DirectoryPath extractDirectoryPath(String exportPathString) {
        Path fullPath = Paths.get(exportPathString);
        int nameCount = fullPath.getNameCount();
        Path root = fullPath.getRoot();

        if (nameCount == 1 && root == null) {
            return new DirectoryPath("./");
        }

        if (nameCount == 1) {
            return new DirectoryPath(root.toString());
        }

        return new DirectoryPath(
                root.toString()
                        + fullPath
                        .subpath(0, nameCount - 1)
                        .toString()
        );
    }

}
