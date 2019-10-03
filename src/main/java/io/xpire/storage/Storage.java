package io.xpire.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import io.xpire.commons.exceptions.DataConversionException;
import io.xpire.model.ReadOnlyExpiryDateTracker;
import io.xpire.model.ReadOnlyUserPrefs;
import io.xpire.model.UserPrefs;

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
