package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;

/**
 * Encapsulates the main storage functions for the main data holders and its corresponding {@code
 * JsonSerializableContent}. {@code T} is the data holder type while {@code U} is the specific JsonSerializableContent
 * type. For example, if T is {@code UniqueFoodList}, then U is {@code JsonSerializableFoodList}.
 */
public interface GeneralStorage<T, U extends JsonSerializableContent<T>> {

    /**
     * Returns the file path of the data file.
     */
    Path getFilePath();

    /**
     * Returns stored data as a {@link T}. Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<T> read() throws DataConversionException, IOException;

    /**
     * @see #read()
     */
    Optional<T> read(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link T} to the storage.
     *
     * @param content cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void save(T content) throws IOException;

    /**
     * @see #save(T)
     */
    void save(T content, Path filePath) throws IOException;

}

