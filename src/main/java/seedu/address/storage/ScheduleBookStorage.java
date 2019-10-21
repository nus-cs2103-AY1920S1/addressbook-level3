package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.schedule.Schedule;

/**
 * Represents a storage for {@link Schedule} {@link seedu.address.model.DataBook}.
 */
public interface ScheduleBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getScheduleBookFilePath();

    /**
     * Returns Schedule DataBook data as a {@link ReadOnlyDataBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyDataBook<Schedule>> readScheduleBook() throws DataConversionException, IOException;

    /**
     * @see #getScheduleBookFilePath()
     */
    Optional<ReadOnlyDataBook<Schedule>> readScheduleBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyDataBook} to the storage.
     * @param scheduleBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveScheduleBook(ReadOnlyDataBook<Schedule> scheduleBook) throws IOException;

    /**
     * @see #saveScheduleBook(ReadOnlyDataBook)
     */
    void saveScheduleBook(ReadOnlyDataBook<Schedule> scheduleBook, Path filePath) throws IOException;

}
