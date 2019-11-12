package seedu.ezwatchlist.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.ezwatchlist.commons.exceptions.DataConversionException;
import seedu.ezwatchlist.model.ReadOnlyUserPrefs;
import seedu.ezwatchlist.model.ReadOnlyWatchList;
import seedu.ezwatchlist.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends WatchListStorage, DatabaseStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getWatchListFilePath();

    @Override
    Path getDatabaseFilePath();

    @Override
    Optional<ReadOnlyWatchList> readWatchList() throws DataConversionException, IOException;

    @Override
    Optional<ReadOnlyWatchList> readDatabase() throws DataConversionException, IOException;

    @Override
    void saveWatchList(ReadOnlyWatchList watchList) throws IOException;

    @Override
    void saveDatabase(ReadOnlyWatchList database) throws IOException;
}
