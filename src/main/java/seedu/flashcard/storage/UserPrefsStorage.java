package seedu.flashcard.storage;

import seedu.flashcard.model.ReadOnlyUserPrefs;
import seedu.flashcard.model.UserPrefs;
import seedu.flashcard.commons.exceptions.DataConversionException;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Represents a storage for {@link seedu.flashcard.model.UserPrefs}
 */
public interface UserPrefsStorage {

    /**
     * Gert the file path of the user prefs data file.
     */
    Path getUserPrefsFilePath();

    /**
     * Get the user prefs data from storage.
     * @return the user prefs read from the storage.
     * @throws DataConversionException if the data in storage is not in expected format.
     * @throws IOException if there was any problem when reading from storage.
     */
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    /**
     * Save the given {@code ReadOnlyUserPrefs} into storage.
     * @param userPrefs cannot be null.
     * @throws IOException if there was any problem writing the file.
     */
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;
}
