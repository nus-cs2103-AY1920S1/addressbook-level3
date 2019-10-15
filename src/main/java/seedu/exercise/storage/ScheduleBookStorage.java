package seedu.exercise.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.exercise.commons.exceptions.DataConversionException;
import seedu.exercise.model.ReadOnlyScheduleBook;

/**
 * Represents a storage for {@link ScheduleBook}
 */
public interface ScheduleBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getScheduleBookFilePath();

    /**
     * Returns ScheduleBook data as a {@link ReadOnlyScheduleBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyScheduleBook> readScheduleBook() throws DataConversionException, IOException;

    /**
     * @see #getScheduleBookFilePath()
     */
    Optional<ReadOnlyScheduleBook> readScheduleBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyScheduleBook} to the storage.
     * @param scheduleBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveScheduleBook(ReadOnlyScheduleBook scheduleBook) throws IOException;

    /**
     * @see #saveScheduleBook(ReadOnlyScheduleBook)
     */
    void saveScheduleBook(ReadOnlyScheduleBook scheduleBook, Path filePath) throws IOException;
}
