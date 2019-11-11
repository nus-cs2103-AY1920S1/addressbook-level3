package seedu.weme.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.weme.commons.exceptions.DataConversionException;
import seedu.weme.model.ReadOnlyUserPrefs;
import seedu.weme.model.ReadOnlyWeme;
import seedu.weme.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends WemeStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getWemeFolderPath();

    @Override
    Optional<ReadOnlyWeme> readWeme() throws DataConversionException, IOException;

    @Override
    void saveWeme(ReadOnlyWeme weme) throws IOException;

}
