package seedu.moolah.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.moolah.commons.exceptions.DataConversionException;
import seedu.moolah.model.ReadOnlyMooLah;
import seedu.moolah.model.ReadOnlyUserPrefs;
import seedu.moolah.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends MooLahStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getMooLahFilePath();

    @Override
    Optional<ReadOnlyMooLah> readMooLah() throws DataConversionException, IOException;

    @Override
    void saveMooLah(ReadOnlyMooLah mooLah) throws IOException;

}
