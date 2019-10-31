package seedu.savenus.storage.alias;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.savenus.commons.exceptions.DataConversionException;
import seedu.savenus.model.alias.AliasList;

/**
 * Creates a storage to store fields for the AliasList.
 */
public interface AliasStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getAliasFilePath();

    /**
     * Returns Alias data as a {@link AliasList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<AliasList> readList() throws DataConversionException, IOException;

    /**
     * @see #getAliasFilePath()
     */
    Optional<AliasList> readList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link AliasList} to the storage.
     * @param list cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveList(AliasList list) throws IOException;

    /**
     * @see #saveList(AliasList)
     */
    void saveList(AliasList list, Path filePath) throws IOException;
}
