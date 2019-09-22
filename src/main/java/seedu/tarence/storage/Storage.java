package seedu.tarence.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.tarence.commons.exceptions.DataConversionException;
import seedu.tarence.model.ReadOnlyApplication;
import seedu.tarence.model.ReadOnlyUserPrefs;
import seedu.tarence.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends ApplicationStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getApplicationFilePath();

    @Override
    Optional<ReadOnlyApplication> readApplication() throws DataConversionException, IOException;

    @Override
    void saveApplication(ReadOnlyApplication application) throws IOException;

}
