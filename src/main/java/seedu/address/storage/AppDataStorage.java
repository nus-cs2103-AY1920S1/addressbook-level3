package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.AppData;
import seedu.address.model.ReadOnlyAppData;

/**
 * Represents a storage for {@link AppData}.
 */
public interface AppDataStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getAppDataFilePath();

    /**
     * Returns AppData data as a {@link ReadOnlyAppData}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAppData> readAppData() throws DataConversionException, IOException;

    /**
     * @see #getAppDataFilePath()
     */
    Optional<ReadOnlyAppData> readAppData(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyAppData} to the storage.
     * @param appData cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAppData(ReadOnlyAppData appData) throws IOException;

    /**
     * @see #saveAppData(ReadOnlyAppData)
     */
    void saveAppData(ReadOnlyAppData appData, Path filePath) throws IOException;
}
