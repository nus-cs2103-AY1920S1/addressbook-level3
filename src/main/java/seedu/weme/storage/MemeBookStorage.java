package seedu.weme.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.weme.commons.exceptions.DataConversionException;
import seedu.weme.model.MemeBook;
import seedu.weme.model.ReadOnlyMemeBook;

/**
 * Represents a storage for {@link MemeBook}.
 */
public interface MemeBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getMemeBookFilePath();

    /**
     * Returns MemeBook data as a {@link ReadOnlyMemeBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyMemeBook> readMemeBook() throws DataConversionException, IOException;

    /**
     * @see #getMemeBookFilePath()
     */
    Optional<ReadOnlyMemeBook> readMemeBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyMemeBook} to the storage.
     * @param memeBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveMemeBook(ReadOnlyMemeBook memeBook) throws IOException;

    /**
     * @see #saveMemeBook(ReadOnlyMemeBook)
     */
    void saveMemeBook(ReadOnlyMemeBook memeBook, Path filePath) throws IOException;

}
