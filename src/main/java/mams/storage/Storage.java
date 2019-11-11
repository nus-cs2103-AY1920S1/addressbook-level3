package mams.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import mams.commons.exceptions.DataConversionException;
import mams.logic.history.ReadOnlyCommandHistory;
import mams.model.ReadOnlyMams;
import mams.model.ReadOnlyUserPrefs;
import mams.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends MamsStorage, UserPrefsStorage, CommandHistoryStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getMamsFilePath();

    @Override
    Optional<ReadOnlyMams> readMams() throws DataConversionException, IOException;

    @Override
    void saveMams(ReadOnlyMams mams) throws IOException;

    @Override
    Optional<ReadOnlyCommandHistory> readCommandHistory() throws DataConversionException, IOException;

    @Override
    void saveCommandHistory(ReadOnlyCommandHistory commandHistory) throws IOException;

}
