package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.calendar.Reminder;

/**
 * Represents a storage for {@link seedu.address.model.calendar.Reminder}.
 */
public interface JsonReminderStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getReminderFilePath();

    /**
     * Returns Reminder data as a ArrayList. Returns an empty list if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    ArrayList<Reminder> readReminder() throws DataConversionException, IOException;

    /**
     * @see #getReminderFilePath()
     */
    ArrayList readReminder(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given list of reminder to the storage.
     *
     * @param reminders cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveReminder(ArrayList<Reminder> reminders) throws IOException;

    /**
     * @see #saveReminder(ArrayList)
     */
    void saveReminder(ArrayList<Reminder> reminders, Path filePath) throws IOException;

}
