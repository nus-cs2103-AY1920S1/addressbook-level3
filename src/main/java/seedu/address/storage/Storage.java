package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.profile.ReadOnlyDukeCooks;
import seedu.address.profile.ReadOnlyUserPrefs;
import seedu.address.profile.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends DukeCooksStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getDukeCooksFilePath();

    @Override
    Optional<ReadOnlyDukeCooks> readDukeCooks() throws DataConversionException, IOException;

    @Override
    void saveDukeCooks(ReadOnlyDukeCooks dukeCooks) throws IOException;

}
