package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyNotebook;

/**
 * Represents a storage for {@link ReadOnlyNotebook}.
 */
public interface NotebookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getNotebookFilePath();

    /**
     * Returns Notebook data as a {@link ReadOnlyNotebook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyNotebook> readNotebook() throws DataConversionException, IOException;

    /**
     * @see #getNotebookFilePath()
     */
    Optional<ReadOnlyNotebook> readNotebook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyNotebook} to the storage.
     * @param notebook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveNotebook(ReadOnlyNotebook notebook) throws IOException;

    /**
     * @see #saveNotebook(ReadOnlyNotebook)
     */
    void saveNotebook(ReadOnlyNotebook notebook, Path filePath) throws IOException;

}
