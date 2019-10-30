package seedu.system.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.system.commons.exceptions.DataConversionException;
import seedu.system.model.ReadOnlyUserPrefs;
import seedu.system.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends SystemStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;
}
