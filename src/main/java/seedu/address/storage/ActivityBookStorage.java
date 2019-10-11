package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ActivityBook;

/**
 * Represents a storage for {@link seedu.address.model.ActivityBook}.
 */
public interface ActivityBookStorage {
   /**
    * Returns the file path of the UserPrefs data file.
    */

    Path getActivityBookFilePath();

    /**
    * Reads data from storage and updates ActivityBook.
    *   Returns {@code Optional.empty()} if storage file is not found.
    * @throws DataConversionException if the data in storage is not in the expected format.
    * @throws IOException if there was any problem when reading from the storage.
    */
    Optional<ActivityBook> readActivityBook() throws DataConversionException, IOException;

    /**
    * Saves the given {@link seedu.address.model.ActivityBook} to the storage.
    * @param state cannot be null.
    * @throws IOException if there was any problem writing to the file.
    */
    void saveActivityBook(ActivityBook activityBook) throws IOException;
}
