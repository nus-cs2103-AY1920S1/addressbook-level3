package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyExpiryDateTracker;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends ExpiryDateTrackerStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getExpiryDateTrackerFilePath();

    @Override
    Optional<ReadOnlyExpiryDateTracker> readExpiryDateTracker() throws DataConversionException, IOException;

    @Override
    void saveExpiryDateTracker(ReadOnlyExpiryDateTracker expiryDateTracker) throws IOException;

}
