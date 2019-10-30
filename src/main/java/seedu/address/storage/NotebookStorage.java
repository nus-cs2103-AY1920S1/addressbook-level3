package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Notebook;

/**
 * Represents a storage for {@link Notebook}.
 */
public interface NotebookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getNotebookFilePath();

    /**
     * Returns Notebook data as a {@link Notebook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<Notebook> readNotebook() throws DataConversionException, IOException;

    /**
     * @see #getNotebookFilePath()
     */
    Optional<Notebook> readNotebook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link Notebook} to the storage.
     * @param notebook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveNotebook(Notebook notebook) throws IOException;

    /**
     * @see #saveNotebook(Notebook)
     */
    void saveNotebook(Notebook notebook, Path filePath) throws IOException;

}
