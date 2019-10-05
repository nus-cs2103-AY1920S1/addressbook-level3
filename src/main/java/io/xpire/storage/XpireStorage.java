package io.xpire.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import io.xpire.commons.exceptions.DataConversionException;
import io.xpire.model.ReadOnlyXpire;
import io.xpire.model.Xpire;

/**
 * Represents a storage for {@link Xpire}.
 */
public interface XpireStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getXpireFilePath();

    /**
     * Returns Xpire data as a {@link ReadOnlyXpire}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyXpire> readXpire() throws DataConversionException, IOException;

    /**
     * @see #getXpireFilePath()
     */
    Optional<ReadOnlyXpire> readXpire(Path filePath) throws DataConversionException,
            IOException;

    /**
     * Saves the given {@link ReadOnlyXpire} to the storage.
     * @param xpire cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveXpire(ReadOnlyXpire xpire) throws IOException;

    /**
     * @see #saveXpire(ReadOnlyXpire)
     */
    void saveXpire(ReadOnlyXpire xpire, Path filePath) throws IOException;

}
