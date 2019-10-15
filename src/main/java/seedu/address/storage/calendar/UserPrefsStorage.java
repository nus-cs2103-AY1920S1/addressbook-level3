package seedu.address.storage.calendar;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.calendar.CalendarUserPrefs;
import seedu.address.model.calendar.ReadOnlyCalendarUserPrefs;




/**
 * Represents a storage for {@link CalendarUserPrefs}.
 */
public interface UserPrefsStorage {

    /**
     * Returns the file path of the CalendarUserPrefs data file.
     */
    Path getUserPrefsFilePath();

    /**
     * Returns CalendarUserPrefs data from storage.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<CalendarUserPrefs> readUserPrefs() throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyCalendarUserPrefs} to the storage.
     * @param userPrefs cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveUserPrefs(ReadOnlyCalendarUserPrefs userPrefs) throws IOException;

}
